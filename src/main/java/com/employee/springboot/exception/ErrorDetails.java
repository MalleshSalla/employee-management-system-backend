package com.employee.springboot.exception;

import java.util.Date;

public class ErrorDetails {
	
	private Date timestamp;
	private String message;
	private String details;
	
	public ErrorDetails(Date date, String message, String details) {
		super();
		this.timestamp = date;
		this.message = message;
		this.details = details;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getDetails() {
		return details;
	}
	

}
