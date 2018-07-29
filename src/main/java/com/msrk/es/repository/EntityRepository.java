package com.msrk.es.repository;

import com.msrk.es.model.Entity;
import com.msrk.es.model.EntityMetaData;

/**
 * @author mdsarfarazkhan
 *
 */
public interface EntityRepository {

	public void addEntityMetaData(EntityMetaData entityMetaData);
	public String add(Entity entity) ;
	public String get(String type,String id);
	public boolean updateEntity(String type,String id,Entity entity);
	public boolean delete(String type,String id);
}
