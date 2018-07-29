package com.msrk.es.model;

/**
 * @author mdsarfarazkhan
 *
 */
public class Entity {

	private long id;
	private String type;
	private String data;
	
	public Entity() {}

	public Entity(String type, String data) {
		super();
		this.type = type;
		this.data = data;
	}
	
	public Entity(long id, String type, String data) {
		super();
		this.id = id;
		this.type = type;
		this.data = data;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
