package com.onlineBanking.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.CreateTransactionRequestDto;
import com.onlineBanking.transaction.service.TransactionService;


@RestController
@RequestMapping("/api/v1/")
public class TransactionController {
	
	private final TransactionService transactionService;
	
	@Autowired	
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}


	@PostMapping("transaction")
	ResponseEntity<String> createTransaction(@RequestBody CreateTransactionRequestDto createTransactionRequestDto) throws TransactionApplicationException{
	    String response = transactionService.createTransaction(createTransactionRequestDto.getUserId(),createTransactionRequestDto.getAmount(), createTransactionRequestDto.getTransactionType());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
 }
