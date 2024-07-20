package com.onlineBanking.transaction.exception;

import org.springframework.http.HttpStatus;

public class DateRangeException extends Exception {

	private static final long serialVersionUID = 4063773034710494023L;
	private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	private String message;

	public DateRangeException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
