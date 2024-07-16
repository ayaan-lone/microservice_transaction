package com.onlineBanking.transaction.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.transaction.entity.Transaction;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.TransactionDetailsDto;
import com.onlineBanking.transaction.response.TransactionResponseDto;
import com.onlineBanking.transaction.service.TransactionService;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/transaction")
	ResponseEntity<String> createTransactionDetails(@RequestBody TransactionDetailsDto transactionDetailsDto) {
		String response = transactionService.createUserTransactions(transactionDetailsDto);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/statement/{userId}")
	ResponseEntity<List<TransactionResponseDto>> getStatement(@PathVariable Long userId) throws TransactionApplicationException {
		List<TransactionResponseDto> response = transactionService.getStatement(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/monthly-statement/{userId}/{monthId}/{year}")
	ResponseEntity<List<TransactionResponseDto>> getMonthlyStatement(@PathVariable Long userId, @PathVariable int monthId,
			@PathVariable int year) throws TransactionApplicationException {
		List<TransactionResponseDto> response = transactionService.getMonthlyStatement(userId, monthId, year);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/quaterly-statement/{userId}/{quater}/{year}")
	ResponseEntity<List<TransactionResponseDto>> getQuaterlyStatement(@PathVariable Long userId, @PathVariable int quater,
			@PathVariable int year) throws TransactionApplicationException {
		List<TransactionResponseDto> response = transactionService.getQuaterlyStatement(userId, quater, year);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/yearly-statement/{userId}/{year}")
	ResponseEntity<List<TransactionResponseDto>> getYearlyStatement(@PathVariable Long userId, @PathVariable int year)
			throws TransactionApplicationException {
		List<TransactionResponseDto> response = transactionService.getYearlyStatement(userId, year);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
