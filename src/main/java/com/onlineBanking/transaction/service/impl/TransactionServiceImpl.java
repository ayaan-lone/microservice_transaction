package com.onlineBanking.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.onlineBanking.transaction.dao.TransactionRepository;
import com.onlineBanking.transaction.entity.Transaction;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.CreateTransactionRequestDto;
import com.onlineBanking.transaction.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public String createTransaction(Long userId, Long amount, String transactionType)
			throws TransactionApplicationException {
		// TODO Auto-generated method stub

		Transaction transaction = new Transaction();
		transaction.setUserId(userId);
		transaction.setAmount(amount);
		transaction.setTransactionType(transactionType);

		String updateAccountBalanceUrl = "http://localhost:8081/api/v1/update-balance";
//		System.out.println("URL CARD : "+createCardUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		// Create request payload
		CreateTransactionRequestDto requestPayload = new CreateTransactionRequestDto();
		requestPayload.setUserId(userId);
		requestPayload.setAmount(amount);
		requestPayload.setTransactionType(transactionType);

		HttpEntity<CreateTransactionRequestDto> requestEntity = new HttpEntity<>(requestPayload, headers);

		// Send POST request to the account service to update the account balance
		ResponseEntity<String> responseEntity = restTemplate.exchange(updateAccountBalanceUrl, HttpMethod.POST,
				requestEntity, String.class);

		if (responseEntity.getStatusCode() != HttpStatus.OK) {
			throw new TransactionApplicationException(HttpStatus.BAD_REQUEST, "Account Balance Updation Failed !");
		}

		transactionRepository.save(transaction);

		return "Transaction Successful !!";
	}

}
