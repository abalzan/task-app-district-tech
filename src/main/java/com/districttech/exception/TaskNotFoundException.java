package com.districttech.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1422352295701568198L;

	public TaskNotFoundException(String message) {
		super(message);
	}

	
}
