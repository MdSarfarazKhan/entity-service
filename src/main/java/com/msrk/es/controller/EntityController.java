package com.msrk.es.controller;



import io.swagger.annotations.ApiOperation;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.msrk.es.model.Entity;
import com.msrk.es.model.EntityMetaData;
import com.msrk.es.service.EntityService;

/**
 * @author mdsarfarazkhan
 *
 */

@RestController
@RequestMapping("/entity")
public class EntityController {
	
	@Autowired
	private EntityService entityService;
	
	
	@ApiOperation(notes = "Creates new collection",value = "Creates new collection")
	@PostMapping(path="/collection/{type}",consumes = MediaType.APPLICATION_JSON_VALUE, produces =  MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createEntityType(@PathVariable(value = "type", required = true) String type,@RequestBody EntityMetaData entityMetaData) {
		entityService.createEntityMetaData(type, entityMetaData);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@ApiOperation(notes = "Get entity object using type and id",value = "Get entity object using type and id")
	@GetMapping(path="/{type}/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> get(@PathVariable(value = "type") String type,@PathVariable(value = "id") String id) {
		String entity = entityService.getEntity(type, id);
		
		return new ResponseEntity<String>(entity,HttpStatus.OK);
	}
	
	@ApiOperation(notes = "Create new entity object for given type",value = "Create new entity object for given type")
	@PostMapping(path="/{type}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createEntity(@PathVariable(value = "type") String type,@RequestBody String entity) {
		String id = entityService.addEntity(new Entity(type,entity));
		Map<String, String> uriVariables = new HashMap<>(2);
		uriVariables.put("type", type);
		uriVariables.put("id", id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(uriVariables).toUri();
		return ResponseEntity.created(uri).build();	
	}
	
	@ApiOperation(notes = "Update existing entity object for given type",value = "Update existing entity object for given type")
	@PutMapping(path="/{type}/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "type") String type,
			@PathVariable(value = "id") String id, @RequestBody String entity) {
		
		entityService.updateEntity(type, id, new Entity(type,entity));
		
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);	
	}
	
	@ApiOperation(notes = "Delete existing entity object for given id",value = "Delete existing entity object for given id")
	@DeleteMapping(path="/{type}/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable(value = "type") String type,@PathVariable(value = "id") String id) {
		entityService.deleteEntity(type, id);
		
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

}
