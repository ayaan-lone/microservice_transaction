package com.onlineBanking.transaction.request;

public class TransactionRequestDto {

	private Long userId; // This should be the User Id
//	private LocalDateTime transactionTime = null; // The time at which the Transaction was made

	private String transactionType; // Withdrawal or Deposit

	private long transactionAmount; // Amount of money to Withdraw or Deposit


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
