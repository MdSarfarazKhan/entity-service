package com.msrk.es.model;

/**
 * @author mdsarfarazkhan
 *
 */
public class Attribute {
	
	private String name;
	private AttributeDataType attributeType;
	private AttributeValidationType attributeValidationType;
	private String attributeValidationData;
	
	public Attribute() {}
	
	public Attribute(String name, AttributeDataType attributeType,
			AttributeValidationType attributeValidationType,
			String attributeValidationData) {
		super();
		this.name = name;
		this.attributeType = attributeType;
		this.attributeValidationType = attributeValidationType;
		this.attributeValidationData = attributeValidationData;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AttributeDataType getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(AttributeDataType attributeType) {
		this.attributeType = attributeType;
	}
	public AttributeValidationType getAttributeValidationType() {
		return attributeValidationType;
	}
	public void setAttributeValidationType(
			AttributeValidationType attributeValidationType) {
		this.attributeValidationType = attributeValidationType;
	}
	public String getAttributeValidationData() {
		return attributeValidationData;
	}
	public void setAttributeValidationData(String attributeValidationData) {
		this.attributeValidationData = attributeValidationData;
	}
	
}
