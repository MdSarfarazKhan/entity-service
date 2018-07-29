package com.msrk.es.service;

import com.msrk.es.model.Entity;
import com.msrk.es.model.EntityMetaData;

/**
 * @author mdsarfarazkhan
 *
 */
public interface EntityService {
	
	public void createEntityMetaData(String collectionType, EntityMetaData entityMetaData);
	public String addEntity(Entity entity);
	public String getEntity(String type,String id);
	public void updateEntity(String type,String id,Entity entity);
	public void deleteEntity(String type,String id);
	
}
