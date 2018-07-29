package com.msrk.es.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


/**
 * @author mdsarfarazkhan
 *
 */

@Configuration
public class EntityConfig {

	@Value("${db.mongo.connection:mongodb://localhost:27017}")
	private String connectionString;
	
	@Value("${db.mongo.dbname:entity:entity}")
	private String dbName;
	
	
	@Bean
	public MongoClient mongoClient() {
	
		MongoClient mongoClient = MongoClients.create(connectionString);
		return mongoClient;
	}
	
	@Bean
	public MongoDatabase mongoDatabase(MongoClient mongoClient) {
		
		MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
		return mongoDatabase;
	}
	
	
}

