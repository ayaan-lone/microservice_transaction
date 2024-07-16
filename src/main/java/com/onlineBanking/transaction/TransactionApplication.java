package com.onlineBanking.transaction;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
//	@Bean
//	public RestTemplate restTemplate() {
//		RestTemplate restTemplate = new RestTemplate();
//
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//
//		restTemplate.setRequestFactory(requestFactory);
//		return restTemplate;
//	}
	
//	@Bean
//	public RestTemplate restTemplate() {
//	    RestTemplate restTemplate = new RestTemplate();
//	    HttpClient httpClient = HttpClientBuilder.create().build();
//	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//	    restTemplate.setRequestFactory(requestFactory);
//	    return restTemplate;
//	}
	
//	@Bean
//    public WebClient.Builder webClientBuilder() {
//        return WebClient.builder();
//    }

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
