package com.msrk.es.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msrk.es.custom.exception.EntityNotFoundException;
import com.msrk.es.custom.exception.EntityUpdateFailedException;
import com.msrk.es.model.Entity;
import com.msrk.es.model.EntityMetaData;
import com.msrk.es.model.util.EntityServiceUtil;
import com.msrk.es.repository.EntityRepository;

/**
 * @author mdsarfarazkhan
 *
 */
@Service
public class EntityServiceImpl implements EntityService{

	@Autowired
	private EntityRepository entityRepository;
	
	@Autowired
	private EntityServiceUtil entityServiceUtil;
	
	@Override
	public void createEntityMetaData(String collectionType, EntityMetaData entityMetaData) {
		if(!entityServiceUtil.isValidString(collectionType))
			throw new RuntimeException("Collection Type cannot be null or empty");
		if(!collectionType.equals(entityMetaData.getName()))
			throw new RuntimeException("Collection type in path and metadata object should be same");
		
		entityRepository.addEntityMetaData(entityMetaData);
	}
	
	public String addEntity(Entity entity) {
		if(!entityServiceUtil.isValidString(entity.getType()))
			throw new RuntimeException("Collection Type cannot be null or empty");
		return entityRepository.add(entity);
	}

	public void deleteEntity(String type, String id) {
		boolean isDeleted = entityRepository.delete(type, id);
		
		if(!entityServiceUtil.isValidString(type))
			throw new RuntimeException("Collection Type cannot be null or empty");
		
		if(!entityServiceUtil.isValidString(id))
			throw new RuntimeException("Id cannot be null or empty");
		
		if(!isDeleted)
			throw new EntityNotFoundException("No Such entity exist for id: "+id+" in type: "+type);
	}

	public String getEntity(String type, String id) {
		if(!entityServiceUtil.isValidString(type))
			throw new RuntimeException("Collection Type cannot be null or empty");
		
		if(!entityServiceUtil.isValidString(id))
			throw new RuntimeException("Id cannot be null or empty");
		
		String entity = entityRepository.get(type, id);
		if(entity == null) {
			throw new EntityNotFoundException("No Such entity exist for id: "+id+" in type: "+type);
		} 
		return entity;
	}

	@Override
	public void updateEntity(String type, String id, Entity entity) {
		
		getEntity(type, id);
		try{
			if(!entityRepository.updateEntity(type, id, entity)) {
				throw new EntityUpdateFailedException("Update Failed");
			}
		} catch(RuntimeException e) {
			throw new EntityUpdateFailedException(e.getMessage());
		}
	}

}
