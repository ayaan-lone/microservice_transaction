package com.onlineBanking.transaction.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.onlineBanking.transaction.dao.TransactionRepository;
import com.onlineBanking.transaction.entity.Transaction;
import com.onlineBanking.transaction.request.TransactionDetailsDto;
import com.onlineBanking.transaction.service.TransactionService;

import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
//	private final RestTemplate restTemplate;
	private final WebClient.Builder webClientBuilder;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository, /*RestTemplate restTemplate,*/
			WebClient.Builder webClientBuilder) {
		this.transactionRepository = transactionRepository;
//		this.restTemplate = restTemplate;
		this.webClientBuilder = webClientBuilder;
	}

	@Value("${onlineBanking.account.url}")
	private String accountBalanceUpdateUrl;

	@Override
	public String createUserTransactions(TransactionDetailsDto transactionDetailsDto) {
		Transaction transaction = new Transaction();
		transaction.setUserId(transactionDetailsDto.getUserId());
		transaction.setAmount(transactionDetailsDto.getAmount());
		transaction.setTransactionType(transactionDetailsDto.getTransactionType());
		transaction.setDateTime(LocalDateTime.now());
		transactionRepository.save(transaction);

//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Content-Type", "application/json");	
//		
//		HttpEntity<TransactionDetailsDto> requestEntity = new HttpEntity<>(transactionDetailsDto, headers);
//		
//		ResponseEntity<String> response = restTemplate.exchange(accountBalanceUpdateUrl, HttpMethod.PATCH, requestEntity, String.class);

		Mono<String> response = webClientBuilder.build().patch().uri(uriBuilder -> uriBuilder.path(accountBalanceUpdateUrl)
                .queryParam("userId", transactionDetailsDto.getUserId())
                .queryParam("amount", transactionDetailsDto.getAmount())
                .queryParam("transactionType", transactionDetailsDto.getTransactionType())
                .build())
				.contentType(MediaType.APPLICATION_JSON).bodyValue(transactionDetailsDto).retrieve()
				.bodyToMono(String.class);

		return response.toString();
	}

}
