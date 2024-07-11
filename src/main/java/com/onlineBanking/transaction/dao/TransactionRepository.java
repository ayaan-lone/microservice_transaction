package com.onlineBanking.transaction.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineBanking.transaction.entity.Transactions;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
	
}
