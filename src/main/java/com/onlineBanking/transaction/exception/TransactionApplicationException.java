package com.onlineBanking.transaction.exception;

import org.springframework.http.HttpStatus;

public class TransactionApplicationException extends Exception {

	private static final long serialVersionUID = -967617117005016707L;

	private HttpStatus httpStatus;
	private String message;

	public TransactionApplicationException(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
