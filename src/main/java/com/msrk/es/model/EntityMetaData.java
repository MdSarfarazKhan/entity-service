package com.msrk.es.model;

import java.util.List;

/**
 * @author mdsarfarazkhan
 *
 */
public class EntityMetaData {
	
	private String name;
	private List<String> required;
	private List<Attribute> attributes;
	
	
	public EntityMetaData() {}
	
	public EntityMetaData(String name, List<String> required,
			List<Attribute> attributes) {
		super();
		this.name = name;
		this.required = required;
		this.attributes = attributes;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getRequired() {
		return required;
	}
	public void setRequired(List<String> required) {
		this.required = required;
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
}
