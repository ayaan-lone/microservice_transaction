package com.onlineBanking.transaction.exception;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionApplicationExceptionHandler {

	@ExceptionHandler(value = { TransactionApplicationException.class })
	ResponseEntity<Object> handleTransactionApplicationException(TransactionApplicationException transactionApplicationException) {
		return ResponseEntity.status(transactionApplicationException.getHttpStatus())
				.body(transactionApplicationException.getMessage());
	}

}
