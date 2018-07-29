package com.msrk.es.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import junit.framework.Assert;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.msrk.es.EntityServiceMain;
import com.msrk.es.model.Entity;
import com.msrk.es.model.EntityMetaData;
import com.msrk.es.repository.EntityRepository;

@SpringBootTest(classes = EntityServiceMain.class)
@RunWith(SpringRunner.class)
@PrepareForTest(Document.class)
public class EntityRepositoryUnitTest extends CommonBaseTest {
	
	@MockBean
	private MongoDatabase database;
	
	@Autowired
	private EntityRepository entityRepository;
	
	@Mock
	private MongoCollection<Document> collection;
	
	@Mock
	private FindIterable<Document> iterable;
	
	@Mock
	private MongoCursor<Document> mongoCursor;
	
	@Mock
	private Document document;
	
	@Mock
	private DeleteResult result;
	
	@Mock
	private UpdateResult updateResult;
	
	
	@Test
	public void testAddEntityMetaData() throws Exception {
		String data = getFile("entity/cricketerEntityMetaData.json");
		EntityMetaData entityMetaData = objectMapper.readValue(data, EntityMetaData.class);
		entityRepository.addEntityMetaData(entityMetaData);
		
		doNothing().when(database).createCollection(isA(String.class), any(CreateCollectionOptions.class));
		verify(database).createCollection(isA(String.class), any(CreateCollectionOptions.class));
		
	}
	

	
	@Test
	public void testGet() throws Exception {
		String data = getFile("entity/cricketer.json");
		String id = "5b59f92f68d9cd0d9501dee1";
		String type = "cricketer";
		
		when(database.getCollection(isA(String.class))).thenReturn(collection);
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		when(collection.find(query)).thenReturn(iterable);
		when(iterable.iterator()).thenReturn(mongoCursor);
		when(mongoCursor.hasNext()).thenReturn(true);
		when(mongoCursor.next()).thenReturn(document);
		when(document.toJson()).thenReturn(data);
		String entity = entityRepository.get(type, id);
		
		verify(database).getCollection(isA(String.class));
		verify(collection).find(any(BasicDBObject.class));
		verify(iterable).iterator();
		verify(mongoCursor).hasNext();
		verify(mongoCursor).next();
		verify(document).toJson();
		
		assertEquals(data, entity);
		
	}
	
	@Test
	public void testDelete() throws Exception {
		String id = "5b59f92f68d9cd0d9501dee1";
		String type = "cricketer";
		
		when(database.getCollection(isA(String.class))).thenReturn(collection);
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		when(collection.deleteOne(query)).thenReturn(result);
		when(result.getDeletedCount()).thenReturn(1L);
		boolean isDeleted = entityRepository.delete(type, id);
		
		verify(database).getCollection(isA(String.class));
		verify(collection).deleteOne(any(BasicDBObject.class));
		verify(result).getDeletedCount();
		
		assertEquals(true, isDeleted);
		
	}
	
}
