package com.onlineBanking.transaction.service;

import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.TransactionDetailsDto;
import com.onlineBanking.transaction.response.TransactionPaginationResponse;

public interface TransactionService {
	
	String createUserTransactions(TransactionDetailsDto transactionDetailsDto);
	
	TransactionPaginationResponse getStatement(int pageNumber, int pageSize, Long userId) throws TransactionApplicationException;

	TransactionPaginationResponse getMonthlyStatement(int pageNumber, int pageSize, Long userId, int monthId, int year) throws TransactionApplicationException;

	TransactionPaginationResponse getYearlyStatement(int pageNumber, int pageSize, Long userId, int year) throws TransactionApplicationException;

	TransactionPaginationResponse getQuaterlyStatement(int pageNumber, int pageSize, Long userId, int quater, int year) throws TransactionApplicationException;

}
