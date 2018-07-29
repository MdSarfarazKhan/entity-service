package com.msrk.es.unit.test;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msrk.es.unit.test.config.EntityTestConfig;


@Import(EntityTestConfig.class)
public abstract class CommonBaseTest {

	@Autowired
	protected ObjectMapper objectMapper;


	protected String getFile(String fileName) {
		String result = "";
		ClassLoader classLoader =  getClass().getClassLoader();
		try {
			result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
