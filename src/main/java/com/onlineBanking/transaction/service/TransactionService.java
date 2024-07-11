package com.onlineBanking.transaction.service;


import java.util.List;

import com.onlineBanking.transaction.entity.Transactions;
import com.onlineBanking.transaction.request.TransactionRequestDto;

public interface TransactionService {
	String createTransaction(TransactionRequestDto transactionRequestDto);

	List<Transactions> getAllTransactions();
}
