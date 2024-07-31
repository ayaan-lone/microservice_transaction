package com.onlineBanking.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.transaction.entity.MonthEnum;
import com.onlineBanking.transaction.entity.TransactionType;
import com.onlineBanking.transaction.exception.DateRangeException;
import com.onlineBanking.transaction.exception.InsufficientFundsException;
import com.onlineBanking.transaction.exception.InvalidAmountException;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.CardTransactionRequestDto;
import com.onlineBanking.transaction.request.TransactionDetailsRequestDto;
import com.onlineBanking.transaction.response.TransactionPaginationResponse;
import com.onlineBanking.transaction.service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	

	@PostMapping("/transaction")
	ResponseEntity<String> transactionDetails(@Valid @RequestHeader("Authorization") String token,
			@RequestBody TransactionDetailsRequestDto transactionDetailsDto, HttpServletRequest request)
			throws TransactionApplicationException, InsufficientFundsException, InvalidAmountException {
		Long userId = (Long) request.getAttribute("userId");
		String response = transactionService.transactionDetails(transactionDetailsDto,token, userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/statement/")
	public ResponseEntity<TransactionPaginationResponse> getStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "transactionType", required = false) TransactionType transactionType,HttpServletRequest request) throws TransactionApplicationException {
		 Long userId = (Long) request.getAttribute("userId");
		TransactionPaginationResponse response = transactionService.getStatement(pageNumber, pageSize, transactionType,
				userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/monthly-statement/{month}")
	public ResponseEntity<TransactionPaginationResponse> getMonthlyStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "transactionType", required = false) TransactionType transactionType,
			@PathVariable MonthEnum month,HttpServletRequest request)
			throws TransactionApplicationException, DateRangeException {
		 Long userId = (Long) request.getAttribute("userId");
		TransactionPaginationResponse response = transactionService.getMonthlyStatement(pageNumber, pageSize, userId,
				month, transactionType);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/quaterly-statement/{quarter}")
	public ResponseEntity<TransactionPaginationResponse> getQuaterlyStatement(
			@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "transactionType", required = false) TransactionType transactionType,
			@PathVariable int quarter,HttpServletRequest request)
	
			throws TransactionApplicationException, DateRangeException {
		 Long userId = (Long) request.getAttribute("userId");
		TransactionPaginationResponse response = transactionService.getQuaterlyStatement(pageNumber, pageSize, userId,
				quarter, transactionType);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/yearly-statement/{year}")
	public ResponseEntity<TransactionPaginationResponse> getYearlyStatement(
	        @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
	        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
	        @RequestParam(name = "transactionType", required = false) TransactionType transactionType,
	        @PathVariable(name = "year") Integer year,
	        HttpServletRequest request) throws TransactionApplicationException, DateRangeException {
	    
	    // Extract userId from the request attribute
	    Long userId = (Long) request.getAttribute("userId");
		    
	    TransactionPaginationResponse response = transactionService.getYearlyStatement(pageNumber, pageSize, userId, year, transactionType);
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@PostMapping("/card-transaction")
	public ResponseEntity<String> handleCardTransaction(@Valid @RequestHeader("Authorization") String token,
			@RequestBody CardTransactionRequestDto cardTransactionRequestDto, HttpServletRequest request)
			throws TransactionApplicationException, InsufficientFundsException, InvalidAmountException {
		Long userId = (Long) request.getAttribute("userId");

		String response = transactionService.handleCardTransaction(cardTransactionRequestDto,token, userId);
		return ResponseEntity.ok(response);
	}

	// API to add funds in credit card
	@PostMapping("/add-funds")
	public ResponseEntity<String> topUpCreditCard(
			@RequestBody CardTransactionRequestDto cardTransactionRequestDto)
			throws TransactionApplicationException, InvalidAmountException, InsufficientFundsException {
		String response = transactionService.addFundsToCreditCard(cardTransactionRequestDto);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
//    @GetMapping("/by-card-number")
//    public TransactionPaginationResponse getTransactionsByCardNumber(
//            @RequestParam int pageNumber,
//            @RequestParam int pageSize,
//            @RequestParam long userId,
//            @RequestParam long cardNumber) throws TransactionApplicationException {
//
//        return transactionService.getTransactionsByCardNumber(pageNumber, pageSize, userId, cardNumber);
//    }

}
