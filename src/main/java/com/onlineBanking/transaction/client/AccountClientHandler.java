package com.onlineBanking.transaction.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.transaction.request.TransactionDetailsRequestDto;

@Component
public class AccountClientHandler {

	private final RestTemplate restTemplate;

	@Value("${onlineBanking.account.url}")
	private String accountUrl;

	@Autowired
	public AccountClientHandler(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String updateBalance(TransactionDetailsRequestDto transactionDetailsRequestDto, String token) {

		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", token);

		System.out.println("This is my Token: " + token);
//	    
//		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<TransactionDetailsRequestDto> requestEntity = new HttpEntity<>(transactionDetailsRequestDto,
				headers);

		ResponseEntity<String> response = restTemplate.exchange(accountUrl + "/update-balance", HttpMethod.POST,
				requestEntity, String.class);

		return response.getBody();
	}

	public Double getBalance(Long userId, String token) {

		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", token);
		HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
		String url = accountUrl + "/balance?userId=" + userId;
		ResponseEntity<Double> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Double.class);
		return response.getBody();
	}

}
