package com.onlineBanking.transaction.request;

import com.onlineBanking.transaction.entity.TransactionType;

public class TransactionDetailsRequestDto {

	private long userId;
	private double amount;
	private TransactionType transactionType;
	
	private long cardNumber;
	

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

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
		this.transactionType = TransactionType.valueOf(transactionType.name().toUpperCase());
	}

}
