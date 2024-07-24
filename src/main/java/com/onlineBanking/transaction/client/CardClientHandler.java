package com.onlineBanking.transaction.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.transaction.entity.CardType;
import com.onlineBanking.transaction.request.CardTransactionRequestDto;

@Component
public class CardClientHandler {
	@Value("${card.service.url}")
	private String cardServiceUrl;

	private final RestTemplate restTemplate;

	public CardClientHandler(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public CardType fetchCardType(long userId, long cardNumber) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String url = cardServiceUrl + "fetchCardType?userId=" + userId + "&cardNumber=" + cardNumber;

		ResponseEntity<CardType> response = restTemplate.exchange(url, HttpMethod.GET, null, CardType.class);
		return response.getBody();
	}

	public double fetchCardBalance(long userId, long cardNumber) {
		String url = cardServiceUrl + "fetchCardBalance?userId=" + userId + "&cardNumber=" + cardNumber;

		 ResponseEntity<Double> response = restTemplate.exchange(url, HttpMethod.GET, null, Double.class);

		return response.getBody();
	}
	
	public String updateCardBalance(CardTransactionRequestDto cardTransactionRequestDto) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = cardServiceUrl + "update-balance?userId=" ;
		HttpEntity<CardTransactionRequestDto> requestEntity = new HttpEntity<>(cardTransactionRequestDto,
				headers);

		ResponseEntity<String> response = restTemplate.exchange(url + "/update-balance", HttpMethod.POST,
				requestEntity, String.class);

		return response.getBody();



	}


}
