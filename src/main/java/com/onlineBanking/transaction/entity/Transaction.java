package com.onlineBanking.transaction.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	@Column
	private long userId;
	@Column
	private double amount;
	@Enumerated(EnumType.STRING)
	@Column
	private TransactionType transactionType;
	@Column
	private LocalDateTime dateTime;
	
//	@Column
//	private long cardNumber;
	
//	@Enumerated(EnumType.STRING)
//	@Column
//	private CardType cardType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		this.transactionType = transactionType;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

//	public CardType getCardType() {
//		return cardType;
//	}
//
//	public void setCardType(CardType cardType) {
//		this.cardType = cardType;
//	}

//	public void setCardNumber(long cardNumber) {
//		this.cardNumber = cardNumber;
//	}
//	public long getCardNumber() {
//		return getCardNumber();
//	}

}
