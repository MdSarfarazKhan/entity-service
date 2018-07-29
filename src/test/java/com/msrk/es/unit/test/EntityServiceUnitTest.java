package com.msrk.es.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.msrk.es.EntityServiceMain;
import com.msrk.es.custom.exception.EntityNotFoundException;
import com.msrk.es.custom.exception.EntityUpdateFailedException;
import com.msrk.es.model.Entity;
import com.msrk.es.model.EntityMetaData;
import com.msrk.es.repository.EntityRepository;
import com.msrk.es.service.EntityService;

@SpringBootTest(classes = EntityServiceMain.class)
@RunWith(SpringRunner.class)
public class EntityServiceUnitTest extends CommonBaseTest {
	
	@MockBean
	private EntityRepository entityRepository;
	@Autowired
	private EntityService entityService;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateEntityMetaData() throws Exception {
		String data = getFile("entity/cricketerEntityMetaData.json");
		EntityMetaData entityMetaData = objectMapper.readValue(data, EntityMetaData.class);
		doNothing().when(entityRepository).addEntityMetaData(any(EntityMetaData.class));
		
		entityService.createEntityMetaData(entityMetaData.getName(), entityMetaData);
		verify(entityRepository).addEntityMetaData(any(EntityMetaData.class));
		
	}
	
	@Test
	public void testCreateEntityMetaDataWithDifferentCollection() throws Exception {
		String data = getFile("entity/cricketerEntityMetaData.json");
		EntityMetaData entityMetaData = objectMapper.readValue(data, EntityMetaData.class);
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Collection type in path and metadata object should be same");
		entityService.createEntityMetaData(entityMetaData.getName()+"test", entityMetaData);
		
	}
	
	@Test
	public void testCreateEntityMetaDataWithCollectionParamNull() throws Exception {
		String data = getFile("entity/cricketerEntityMetaData.json");
		EntityMetaData entityMetaData = objectMapper.readValue(data, EntityMetaData.class);
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Collection Type cannot be null or empty");
		entityService.createEntityMetaData(null, entityMetaData);
		
	}
	
	@Test
	public void testAddEntity() throws Exception {
		String data = getFile("entity/cricketer.json");
		String id = UUID.randomUUID().toString();
		Entity entity = new Entity("cricketer", data);
		when(entityRepository.add(any(Entity.class))).thenReturn(id);
		
		String newId = entityService.addEntity(entity);
		
		verify(entityRepository).add(entity);
		assertEquals(id, newId);
	}
	
	@Test
	public void testAddEntityWithInvalidCollectionType() throws Exception {
		String data = getFile("entity/cricketer.json");
		Entity entity = new Entity(null, data);
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Collection Type cannot be null or empty");
		entityService.addEntity(entity);
		
	}
	
	@Test
	public void testDeleteEntity() throws Exception {
		String type = "cricketer";
		String id = UUID.randomUUID().toString();
		
		when(entityRepository.delete(isA(String.class), isA(String.class))).thenReturn(true);
		
		entityService.deleteEntity(type, id);
		
		verify(entityRepository).delete(isA(String.class), isA(String.class));
		
	}
	
	@Test
	public void testDeleteEntityFail() throws Exception {
		String type = "cricketer";
		String id = UUID.randomUUID().toString();
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage("No Such entity exist for id: "+id+" in type: "+type);
		when(entityRepository.delete(isA(String.class), isA(String.class))).thenReturn(false);
		
		entityService.deleteEntity(type, id);
		
		verify(entityRepository).delete(isA(String.class), isA(String.class));
		
	}
	
	@Test
	public void testDeleteEntityWithInvalidType() throws Exception {
		String id = UUID.randomUUID().toString();
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Collection Type cannot be null or empty");
		entityService.deleteEntity(null, id);
	}
	
	@Test
	public void testDeleteEntityWithInvalidId() throws Exception {
		String type = "cricketer";
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Id cannot be null or empty");
		entityService.deleteEntity(type, null);
	}

	
	@Test
	public void testGetEntity() throws Exception {
		String data = getFile("entity/cricketer.json");
		String type = "cricketer";
		String id = UUID.randomUUID().toString();
		
		when(entityRepository.get(isA(String.class), isA(String.class))).thenReturn(data);
		
		String entity = entityService.getEntity(type, id);
		
		verify(entityRepository).get(isA(String.class), isA(String.class));
		assertEquals(data, entity);
		
	}
	
	@Test
	public void testGetEntityInvalidCollectionType() throws Exception {
		
		String id = UUID.randomUUID().toString();
		thrown.expect(RuntimeException.class);
		//thrown.expectMessage("Id cannot be null or empty");
		thrown.expectMessage("Collection Type cannot be null or empty");
		
		entityService.getEntity(null, id);
		
		
	}
	
	@Test
	public void testGetEntityInvalidId() throws Exception {
		String type = "cricketer";
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Id cannot be null or empty");
		
		entityService.getEntity(type, null);
		
		
	}
	
	@Test
	public void testGetEntityNotFound() throws Exception {
		String type = "cricketer";
		String id = UUID.randomUUID().toString();
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage("No Such entity exist for id: "+id+" in type: "+type);
		when(entityRepository.get(isA(String.class), isA(String.class))).thenReturn(null);
		
		entityService.getEntity(type, id);
		
		verify(entityRepository).get(isA(String.class), isA(String.class));
		
	}
	
	@Test
	public void testUpdateEntity() throws Exception {
		String data = getFile("entity/cricketer.json");
		String type = "cricketer";
		String id = UUID.randomUUID().toString();
		Entity entity = new Entity(type, data);
		when(entityRepository.get(isA(String.class), isA(String.class))).thenReturn(data);
		when(entityRepository.updateEntity(isA(String.class), isA(String.class), any(Entity.class))).thenReturn(true);
		
		entityService.updateEntity(type, id, entity);
		
		verify(entityRepository).get(isA(String.class), isA(String.class));
		verify(entityRepository).updateEntity(isA(String.class), isA(String.class), any(Entity.class));
		
	}
	
	@Test
	public void testUpdateEntityFail() throws Exception {
		String data = getFile("entity/cricketer.json");
		String type = "cricketer";
		String id = UUID.randomUUID().toString();
		Entity entity = new Entity(type, data);
		thrown.expect(EntityUpdateFailedException.class);
		thrown.expectMessage("Update Failed");
		when(entityRepository.get(isA(String.class), isA(String.class))).thenReturn(data);
		when(entityRepository.updateEntity(isA(String.class), isA(String.class), any(Entity.class))).thenReturn(false);
		
		entityService.updateEntity(type, id, entity);
		
		verify(entityRepository).get(isA(String.class), isA(String.class));
		verify(entityRepository).updateEntity(isA(String.class), isA(String.class), any(Entity.class));
		
	}
	
	@Test
	public void testUpdateEntityFailRepo() throws Exception {
		String data = getFile("entity/cricketer.json");
		String type = "cricketer";
		String updateFailMsg = "Update Fail due to some internal error";
		String id = UUID.randomUUID().toString();
		Entity entity = new Entity(type, data);
		thrown.expect(EntityUpdateFailedException.class);
		thrown.expectMessage(updateFailMsg);
		when(entityRepository.get(isA(String.class), isA(String.class))).thenThrow(new EntityUpdateFailedException(updateFailMsg));
		when(entityRepository.updateEntity(isA(String.class), isA(String.class), any(Entity.class))).thenReturn(false);
		
		entityService.updateEntity(type, id, entity);
		
		verify(entityRepository).get(isA(String.class), isA(String.class));
		verify(entityRepository).updateEntity(isA(String.class), isA(String.class), any(Entity.class));
		
	}
	
}
