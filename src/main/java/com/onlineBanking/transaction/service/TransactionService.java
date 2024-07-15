package com.onlineBanking.transaction.service;

import com.onlineBanking.transaction.exception.TransactionApplicationException;

public interface TransactionService {

	String createTransaction(Long userId, Long amount, String transactionType) throws TransactionApplicationException ;

}
