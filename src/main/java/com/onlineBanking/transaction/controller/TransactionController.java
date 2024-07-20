package com.onlineBanking.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.transaction.exception.DateRangeException;
import com.onlineBanking.transaction.exception.InsufficientFundsException;
import com.onlineBanking.transaction.exception.InvalidAmountException;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.TransactionDetailsRequestDto;
import com.onlineBanking.transaction.response.TransactionPaginationResponse;
import com.onlineBanking.transaction.service.TransactionService;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/transaction")
	ResponseEntity<String> transactionDetails(@RequestBody TransactionDetailsRequestDto transactionDetailsDto)
			throws TransactionApplicationException, InsufficientFundsException, InvalidAmountException {
		String response = transactionService.transactionDetails(transactionDetailsDto);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/statement/{userId}")
	ResponseEntity<TransactionPaginationResponse> getStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @PathVariable Long userId)
			throws TransactionApplicationException {
		TransactionPaginationResponse response = transactionService.getStatement(pageNumber, pageSize, userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/monthly-statement/{userId}/{month}")
	ResponseEntity<TransactionPaginationResponse> getMonthlyStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @PathVariable Long userId,
			@PathVariable String month) throws TransactionApplicationException, DateRangeException {
		TransactionPaginationResponse response = transactionService.getMonthlyStatement(pageNumber, pageSize, userId,
				month);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/quaterly-statement/{userId}/{quater}")
	ResponseEntity<TransactionPaginationResponse> getQuaterlyStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @PathVariable Long userId,
			@PathVariable int quater) throws TransactionApplicationException, DateRangeException {
		TransactionPaginationResponse response = transactionService.getQuaterlyStatement(pageNumber, pageSize, userId,
				quater);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/yearly-statement/{userId}/{year}")
	ResponseEntity<TransactionPaginationResponse> getYearlyStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @PathVariable Long userId,
			@PathVariable int year) throws TransactionApplicationException, DateRangeException {
		TransactionPaginationResponse response = transactionService.getYearlyStatement(pageNumber, pageSize, userId,
				year);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
