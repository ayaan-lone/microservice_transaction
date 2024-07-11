package com.onlineBanking.transaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.transaction.entity.Transactions;
import com.onlineBanking.transaction.request.TransactionRequestDto;
import com.onlineBanking.transaction.service.TransactionService;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/")
@RestController
public class TransactionController {

	private final TransactionService transactionService;

	@Autowired
		public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
   
	
	@PostMapping("create-transaction")
	public ResponseEntity<String> registerUser(
			@Valid @RequestBody TransactionRequestDto transactionRequestDto){
		String response = transactionService.createTransaction(transactionRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	
	@GetMapping("fetch-all-transactions")
	public ResponseEntity<List<Transactions>> getAllUsers() {
		List<Transactions> transactions = transactionService.getAllTransactions();
		return ResponseEntity.ok(transactions);
	}


}
