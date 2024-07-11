package com.onlineBanking.transaction.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineBanking.transaction.dao.TransactionRepository;
import com.onlineBanking.transaction.entity.Transactions;
import com.onlineBanking.transaction.request.TransactionRequestDto;
import com.onlineBanking.transaction.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper) {

		this.modelMapper = modelMapper;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public String createTransaction(TransactionRequestDto TransactionRequestDto) {

		Transactions transaction = modelMapper.map(TransactionRequestDto, Transactions.class);
		transactionRepository.save(transaction);
		return "Transaction Has been Created";
	}

	@Override
	public List<Transactions> getAllTransactions() {
		// TODO Auto-generated method stub
		
			return transactionRepository.findAll();
		
	}

}
