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

import com.onlineBanking.transaction.entity.MonthEnum;
import com.onlineBanking.transaction.entity.TransactionType;
import com.onlineBanking.transaction.exception.DateRangeException;
import com.onlineBanking.transaction.exception.InsufficientFundsException;
import com.onlineBanking.transaction.exception.InvalidAmountException;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.TopUpCreditCardRequestDto;
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
	public ResponseEntity<TransactionPaginationResponse> getStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "transactionType", required = false) TransactionType transactionType,
			@PathVariable Long userId) throws TransactionApplicationException {
		TransactionPaginationResponse response = transactionService.getStatement(pageNumber, pageSize, transactionType,
				userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/monthly-statement/{userId}/{month}")
	public ResponseEntity<TransactionPaginationResponse> getMonthlyStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "transactionType", required = false) TransactionType transactionType,
			@PathVariable Long userId, @PathVariable MonthEnum month)
			throws TransactionApplicationException, DateRangeException {
		TransactionPaginationResponse response = transactionService.getMonthlyStatement(pageNumber, pageSize, userId,
				month, transactionType);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/quaterly-statement/{userId}/{quarter}")
	public ResponseEntity<TransactionPaginationResponse> getQuaterlyStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "transactionType", required = false) TransactionType transactionType,
			@PathVariable Long userId, @PathVariable int quarter)
			throws TransactionApplicationException, DateRangeException {
		TransactionPaginationResponse response = transactionService.getQuaterlyStatement(pageNumber, pageSize, userId,
				quarter, transactionType);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/yearly-statement/{userId}/{year}")
	public ResponseEntity<TransactionPaginationResponse> getYearlyStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "transactionType", required = false) TransactionType transactionType,
			@PathVariable Long userId, @PathVariable int year)
			throws TransactionApplicationException, DateRangeException {
		TransactionPaginationResponse response = transactionService.getYearlyStatement(pageNumber, pageSize, userId,
				year, transactionType);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/card-transaction")
	public ResponseEntity<String> handleCardTransaction(@RequestParam(name = "userId", required = true) long userId,
			@RequestParam(name = "cardNumber", required = true) long cardNumber,
			@RequestParam(name = "amount", required = true) long amount)
			throws TransactionApplicationException, InsufficientFundsException, InvalidAmountException {

		String response = transactionService.handleCardTransaction(userId, cardNumber, amount);
		return ResponseEntity.ok(response);
	}

	// API to add funds in credit card
	@PostMapping("/add-funds")
	public ResponseEntity<String> topUpCreditCard(
			@RequestBody TopUpCreditCardRequestDto topUpCreditCardRequestDto)
			throws TransactionApplicationException, InvalidAmountException, InsufficientFundsException {
		String response = transactionService.addFundsToCreditCard(topUpCreditCardRequestDto);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
