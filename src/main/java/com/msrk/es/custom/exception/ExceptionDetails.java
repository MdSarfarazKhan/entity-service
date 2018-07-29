package com.msrk.es.custom.exception;

import java.util.Date;


/**
 * @author mdsarfarazkhan
 *
 */
public class ExceptionDetails {
	
	private String errorMsg;
	private String description;
	private Date timestamp;
	
	public ExceptionDetails(String errorMsg, String description, Date timestamp) {
		this.errorMsg = errorMsg;
		this.description = description;
		this.timestamp = timestamp;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getDescription() {
		return description;
	}

	public Date getTimestamp() {
		return timestamp;
	}

}
