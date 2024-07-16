package com.onlineBanking.transaction.service;

import java.util.List;

import com.onlineBanking.transaction.entity.Transaction;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.TransactionDetailsDto;

public interface TransactionService {
	
	String createUserTransactions(TransactionDetailsDto transactionDetailsDto);
	
	List<Transaction> getStatement(Long userId) throws TransactionApplicationException;

	List<Transaction> getMonthlyStatement(Long userId, int monthId, int year) throws TransactionApplicationException;

}
