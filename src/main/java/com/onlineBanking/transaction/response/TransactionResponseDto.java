package com.onlineBanking.transaction.response;

import java.time.LocalDateTime;

import com.onlineBanking.transaction.entity.TransactionType;

public class TransactionResponseDto {

	private double amount;
	private TransactionType transactionType;
	private LocalDateTime dateTime;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

}
