package com.msrk.es.custom.exception;


/**
 * @author mdsarfarazkhan
 *
 */

public class EntityUpdateFailedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EntityUpdateFailedException(String msg) {
		super(msg);
	}

}
