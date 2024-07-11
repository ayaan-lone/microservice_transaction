package com.onlineBanking.transaction.request;

public class TransactionRequestDto {

	private Long id; // This should be the User Id
//	private LocalDateTime transactionTime = null; // The time at which the Transaction was made

	private String transactionType; // Withdrawal or Deposit

	private long transactionAmount; // Amount of money to Withdraw or Deposit

//	private long transactionid; // This would be used to Identify or Search a Transaction

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

//	public LocalDateTime getTransactionTime() {
//		return transactionTime;
//	}
//
//	public void setTransactionTime(LocalDateTime transactionTime) {
//		this.transactionTime = transactionTime;
//	}

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
