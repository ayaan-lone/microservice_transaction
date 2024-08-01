package com.onlineBanking.transaction.service;

import com.onlineBanking.transaction.entity.MonthEnum;
import com.onlineBanking.transaction.entity.TransactionType;
import com.onlineBanking.transaction.exception.DateRangeException;
import com.onlineBanking.transaction.exception.InsufficientFundsException;
import com.onlineBanking.transaction.exception.InvalidAmountException;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.CardTransactionRequestDto;
import com.onlineBanking.transaction.request.TransactionDetailsRequestDto;
import com.onlineBanking.transaction.response.TransactionPaginationResponse;

import jakarta.validation.Valid;

public interface TransactionService {
	
	String transactionDetails(TransactionDetailsRequestDto transactionDetailsDto, String token, Long userId) throws TransactionApplicationException, InsufficientFundsException, InvalidAmountException;
	
	TransactionPaginationResponse getStatement(int pageNumber, int pageSize, TransactionType transactionType, Long userId) throws TransactionApplicationException;

	TransactionPaginationResponse getMonthlyStatement(int pageNumber, int pageSize, Long userId, MonthEnum month, TransactionType transactionType)
	        throws TransactionApplicationException, DateRangeException;

	 TransactionPaginationResponse getQuaterlyStatement(int pageNumber, int pageSize, Long userId, int quarter, TransactionType transactionType)
		        throws TransactionApplicationException, DateRangeException;

	TransactionPaginationResponse getYearlyStatement(int pageNumber, int pageSize, Long userId, int year,
			TransactionType transactionType) throws TransactionApplicationException, DateRangeException;

	String handleCardTransaction(CardTransactionRequestDto cardTransactionRequestDto, @Valid String token, Long userId) throws TransactionApplicationException, InsufficientFundsException, InvalidAmountException;

	String addFundsToCreditCard(CardTransactionRequestDto cardTransactionRequestDto, Long userId) throws TransactionApplicationException, InvalidAmountException, InsufficientFundsException;
	
//    TransactionPaginationResponse getTransactionsByCardNumber(int pageNumber, int pageSize, long userId, long cardNumber) 
//            throws TransactionApplicationException;
    
}
