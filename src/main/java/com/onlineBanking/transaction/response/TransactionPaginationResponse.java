package com.onlineBanking.transaction.response;

import java.util.List;

public class TransactionPaginationResponse {

	private int pageNo;
	private int pageSize;
	private int totalPages;
	private Long totalCount;
	private List<TransactionResponseDto> transactionList;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public List<TransactionResponseDto> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<TransactionResponseDto> transactionList) {
		this.transactionList = transactionList;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

}
