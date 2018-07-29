package com.msrk.es.unit.test.config;

import java.text.SimpleDateFormat;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author mdsarfarazkhan
 *
 */
@TestConfiguration
@ComponentScan(basePackages = "com.msrk.es")
public class EntityTestConfig {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	
	@Bean
	public ObjectMapper objectMapper(){
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.setDateFormat( new SimpleDateFormat(DATE_FORMAT));
		objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		return objectMapper;
	}

}
