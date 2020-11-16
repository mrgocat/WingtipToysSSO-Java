package com.wingtip.sso.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = -7810009995873102599L;

	public ServiceException(String msg) {
		super(msg);
	}
}
