package com.onlineBanking.transaction.exception;

import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends Exception {
	private static final long serialVersionUID = -2032338467567790404L;

	private final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
	private final String message = "Insufficient funds in the account";

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

}
