package com.onlineBanking.transaction.service;

import com.onlineBanking.transaction.exception.DateRangeException;
import com.onlineBanking.transaction.exception.InsufficientFundsException;
import com.onlineBanking.transaction.exception.InvalidAmountException;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.TransactionDetailsRequestDto;
import com.onlineBanking.transaction.response.TransactionPaginationResponse;

public interface TransactionService {
	
	String transactionDetails(TransactionDetailsRequestDto transactionDetailsDto) throws TransactionApplicationException, InsufficientFundsException, InvalidAmountException;
	
	TransactionPaginationResponse getStatement(int pageNumber, int pageSize, Long userId) throws TransactionApplicationException;

	TransactionPaginationResponse getMonthlyStatement(int pageNumber, int pageSize, Long userId, String month) throws TransactionApplicationException, DateRangeException;

	TransactionPaginationResponse getYearlyStatement(int pageNumber, int pageSize, Long userId, int year) throws TransactionApplicationException, DateRangeException;

	TransactionPaginationResponse getQuaterlyStatement(int pageNumber, int pageSize, Long userId, int quater) throws TransactionApplicationException, DateRangeException;

}
