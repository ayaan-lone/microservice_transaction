package com.onlineBanking.transaction.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transactions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4554255809712392042L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id; // This should be the User Id

//	@Column
//	private long transactionNumber; // This would be used to Identify or Search a Transaction

	@Column
	private LocalDateTime transactionTime = LocalDateTime.now(); // The time at which the Transaction was made

	@Column
	private String transactionType; // Withdrawal or Deposit

	@Column
	private long transactionAmount; // Amount of money to Withdraw or Deposit

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public long getTransactionid() {
//		return transactionid;
//	}
//
//	public void setTransactionid(long transactionid) {
//		this.transactionid = transactionid;
//	}

	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public long getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(long transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

}
