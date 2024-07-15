package com.onlineBanking.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.transaction.request.TransactionDetailsDto;
import com.onlineBanking.transaction.service.TransactionService;


@RestController
@RequestMapping("/api/v1")
public class TransactionController {
	
	private final TransactionService transactionService;	
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/transaction")
	ResponseEntity<String> createTransactionDetails(@RequestBody TransactionDetailsDto transactionDetailsDto){
		String response = transactionService.createUserTransactions(transactionDetailsDto);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
