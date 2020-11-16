package com.wingtip.sso.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 6770302264404355226L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
