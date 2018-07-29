package com.msrk.es.repository;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ValidationAction;
import com.mongodb.client.model.ValidationLevel;
import com.mongodb.client.model.ValidationOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.msrk.es.model.Attribute;
import com.msrk.es.model.Entity;
import com.msrk.es.model.EntityMetaData;
import com.msrk.es.model.util.EntityServiceUtil;

/**
 * @author mdsarfarazkhan
 *
 */
@Repository
public class EntityRepositoryImpl implements EntityRepository{

	@Autowired
	private MongoDatabase database;
	
	@Autowired
	private EntityServiceUtil entityServiceUtil;
	
	
	@Override
	public void addEntityMetaData(EntityMetaData entityMetaData) {
		
		List<Attribute> attributes = entityMetaData.getAttributes();
		List<String> requiredFields = entityMetaData.getRequired();
		
		JSONObject root = new JSONObject();
		root.put("required", requiredFields);
		
		JSONObject rootChild1 = new JSONObject();
		root.put("properties", rootChild1);
		for(Attribute attribute : attributes) {
			JSONObject object = new JSONObject();
			object.put("bsonType", attribute.getAttributeType().toString().toLowerCase());
			rootChild1.put(attribute.getName(), object);
			
			switch (attribute.getAttributeValidationType()!= null?attribute.getAttributeValidationType().toString() : "") {
			case "REGEX":
				if(entityServiceUtil.isValidString(attribute.getAttributeValidationData()))
					object.put("pattern", attribute.getAttributeValidationData());
				break;
			
			case "SET":
				if(entityServiceUtil.isValidString(attribute.getAttributeValidationData()))
					object.put("enum", entityServiceUtil.getValues(attribute.getAttributeValidationData()));
				break;
				
			case "RANGE":
				if(entityServiceUtil.isValidString(attribute.getAttributeValidationData())) {
					String[] range = attribute.getAttributeValidationData().trim().split("-");
					int min = Integer.valueOf(range[0].trim());
					int max = Integer.valueOf(range[1].trim());
					if(min > max) {
						throw new RuntimeException(attribute.getName()+ " has invalid range, min value cannot be greater than max value");
					}
					object.put("minimum", min);
					object.put("maximum", max);
					object.put("description", "must be an integer in [ "+min+", "+max+" ]");
				}
					
				break;

			default:
				break;
			}
		}
		
		Document doc = Document.parse(root.toString());
		Bson validator = Filters.jsonSchema(doc);
		ValidationOptions validationOptions = new ValidationOptions().validator(validator);
		validationOptions.validationAction(ValidationAction.ERROR);
		validationOptions.validationLevel(ValidationLevel.MODERATE);
		CreateCollectionOptions collectionOptions = new CreateCollectionOptions();
		collectionOptions.validationOptions(validationOptions);
		database.createCollection(entityMetaData.getName(), collectionOptions);
		
	}
	
	public String add(Entity entity) {
		MongoCollection<Document> collection = database.getCollection(entity.getType());
		Document doc = Document.parse(entity.getData());
		collection.insertOne(doc);
		return doc.get("_id").toString();
		
	}

	public String get(String type, String id) {
		MongoCollection<Document> collection = database.getCollection(type);
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		FindIterable<Document> iterable = collection.find(query);
		MongoCursor<Document> mongoCursor = iterable.iterator();
		String entity = null;
		if(mongoCursor.hasNext()) {
			entity = mongoCursor.next().toJson();
		}
		
		return entity;
	}

	public boolean delete(String type, String id) {
		

		MongoCollection<Document> collection = database.getCollection(type);
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		DeleteResult result = collection.deleteOne(query);
		
		return result.getDeletedCount() > 0;
	}

	@Override
	public boolean updateEntity(String type, String id, Entity entity) {

		MongoCollection<Document> collection = database.getCollection(entity.getType());
		Document doc = Document.parse(entity.getData());
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		UpdateResult updateResult = collection.replaceOne(query, doc);
		
		return updateResult.getModifiedCount() > 0;
		
	
	}
	
}
