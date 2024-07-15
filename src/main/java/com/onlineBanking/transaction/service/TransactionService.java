package com.onlineBanking.transaction.service;

import com.onlineBanking.transaction.request.TransactionDetailsDto;

public interface TransactionService {
	
	String createUserTransactions(TransactionDetailsDto transactionDetailsDto);

}
