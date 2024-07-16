package com.onlineBanking.transaction.service;

import java.util.List;

import com.onlineBanking.transaction.entity.Transaction;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.TransactionDetailsDto;
import com.onlineBanking.transaction.response.TransactionResponseDto;

public interface TransactionService {
	
	String createUserTransactions(TransactionDetailsDto transactionDetailsDto);
	
	List<TransactionResponseDto> getStatement(Long userId) throws TransactionApplicationException;

	List<TransactionResponseDto> getMonthlyStatement(Long userId, int monthId, int year) throws TransactionApplicationException;

	List<TransactionResponseDto> getYearlyStatement(Long userId, int year) throws TransactionApplicationException;

	List<TransactionResponseDto> getQuaterlyStatement(Long userId, int quater, int year) throws TransactionApplicationException;

}
