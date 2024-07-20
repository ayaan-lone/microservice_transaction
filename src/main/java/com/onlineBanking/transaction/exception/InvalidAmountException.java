package com.onlineBanking.transaction.exception;

import org.springframework.http.HttpStatus;

public class InvalidAmountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5677369718790979679L;
	private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	private final String message = "Enter a valid amount i.e. greater than 0";

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

}
