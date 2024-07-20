package com.onlineBanking.transaction.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class TransactionApplicationExceptionHandling {

	@ExceptionHandler(value = { TransactionApplicationException.class })
	ResponseEntity<Object> handleTransactionException(TransactionApplicationException transactionApplicationException) {
		return ResponseEntity.status(transactionApplicationException.getHttpStatus())
				.body(transactionApplicationException.getMessage());
	}

	@ExceptionHandler(value = { DateRangeException.class })
	ResponseEntity<Object> handleDateRandeException(DateRangeException dateRangeException) {
		return ResponseEntity.status(dateRangeException.getHttpStatus()).body(dateRangeException.getMessage());
	}

	@ExceptionHandler(value = { InsufficientFundsException.class })
	ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException insufficientFundsException) {
		return ResponseEntity.status(insufficientFundsException.getHttpStatus())
				.body(insufficientFundsException.getMessage());
	}

	@ExceptionHandler(value = { HttpClientErrorException.class })
	ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException httpClientErrorException) {
		return ResponseEntity.status(httpClientErrorException.getStatusCode())
				.body(httpClientErrorException.getMessage());
	}

	@ExceptionHandler(value = { InvalidAmountException.class })
	ResponseEntity<Object> handleInvalidAmountException(InvalidAmountException invalidAmountException) {
		return ResponseEntity.status(invalidAmountException.getHttpStatus()).body(invalidAmountException.getMessage());
	}
}
