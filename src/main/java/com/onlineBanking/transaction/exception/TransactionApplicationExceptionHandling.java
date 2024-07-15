package com.onlineBanking.transaction.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionApplicationExceptionHandling {

	@ExceptionHandler(value = { TransactionApplicationException.class })
	ResponseEntity<Object> handleTransactionException(TransactionApplicationException transactionApplicationException) {
		return ResponseEntity.status(transactionApplicationException.getHttpStatus())
				.body(transactionApplicationException.getMessage());
	}
}
