package com.msrk.es.model.util;

import org.springframework.stereotype.Component;

/**
 * @author mdsarfarazkhan
 *
 */
@Component
public class EntityServiceUtil {
	
	public String[] getValues(String values) {
		String[] data = values.trim().split(",");
		for(int i = 0; i<data.length; i++) {
			data[i] = data[i].trim();
		}
		return data;
	}

	public boolean isValidString(String str) {
		if(str == null || str.isEmpty())
			return false;
		
		return true;
	}

}
