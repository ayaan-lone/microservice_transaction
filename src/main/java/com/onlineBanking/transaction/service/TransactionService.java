package com.onlineBanking.transaction.service;

import com.onlineBanking.transaction.entity.MonthEnum;
import com.onlineBanking.transaction.entity.TransactionType;
import com.onlineBanking.transaction.exception.DateRangeException;
import com.onlineBanking.transaction.exception.InsufficientFundsException;
import com.onlineBanking.transaction.exception.InvalidAmountException;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.TopUpCreditCardRequestDto;
import com.onlineBanking.transaction.request.TransactionDetailsRequestDto;
import com.onlineBanking.transaction.response.TransactionPaginationResponse;

public interface TransactionService {
	
	String transactionDetails(TransactionDetailsRequestDto transactionDetailsDto) throws TransactionApplicationException, InsufficientFundsException, InvalidAmountException;
	
	TransactionPaginationResponse getStatement(int pageNumber, int pageSize, TransactionType transactionType, Long userId) throws TransactionApplicationException;

	TransactionPaginationResponse getMonthlyStatement(int pageNumber, int pageSize, Long userId, MonthEnum month, TransactionType transactionType)
	        throws TransactionApplicationException, DateRangeException;

	 TransactionPaginationResponse getQuaterlyStatement(int pageNumber, int pageSize, Long userId, int quarter, TransactionType transactionType)
		        throws TransactionApplicationException, DateRangeException;

	TransactionPaginationResponse getYearlyStatement(int pageNumber, int pageSize, Long userId, int year,
			TransactionType transactionType) throws TransactionApplicationException, DateRangeException;

	String handleCardTransaction(long userId, long cardNumber, double amount) throws TransactionApplicationException, InsufficientFundsException, InvalidAmountException;

	String addFundsToCreditCard(TopUpCreditCardRequestDto topUpCreditCardRequestDto) throws TransactionApplicationException, InvalidAmountException, InsufficientFundsException;

}
