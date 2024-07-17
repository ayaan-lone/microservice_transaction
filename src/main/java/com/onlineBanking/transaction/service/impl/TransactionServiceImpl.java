package com.onlineBanking.transaction.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.transaction.dao.TransactionRepository;
import com.onlineBanking.transaction.entity.Transaction;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.TransactionDetailsDto;
import com.onlineBanking.transaction.response.TransactionPaginationResponse;
import com.onlineBanking.transaction.response.TransactionResponseDto;
import com.onlineBanking.transaction.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final RestTemplate restTemplate;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository, RestTemplate restTemplate) {
		this.transactionRepository = transactionRepository;
		this.restTemplate = restTemplate;
	}

	@Value("${onlineBanking.account.url}")
	public String accountBalanceUpdateUrl;

	@Override
	public String createUserTransactions(TransactionDetailsDto transactionDetailsDto) {
		Transaction transaction = new Transaction();
		transaction.setUserId(transactionDetailsDto.getUserId());
		transaction.setAmount(transactionDetailsDto.getAmount());
		transaction.setTransactionType(transactionDetailsDto.getTransactionType());
		transaction.setDateTime(LocalDateTime.now());
		transactionRepository.save(transaction);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("Content-Type", "application/json");	

		HttpEntity<TransactionDetailsDto> requestEntity = new HttpEntity<>(transactionDetailsDto, headers);

		ResponseEntity<String> response = restTemplate.exchange(accountBalanceUpdateUrl, HttpMethod.POST, requestEntity,
				String.class);

		return response.toString();
	}

	private List<Transaction> isUserPersist(Long userId) throws TransactionApplicationException {
		Optional<List<Transaction>> optionalTransaction = transactionRepository.findByUserId(userId);
		if (!optionalTransaction.isPresent()) {
			throw new TransactionApplicationException(HttpStatus.NOT_FOUND, "User is not present!");
		}
		return optionalTransaction.get();
	}

	// This function converts the transaction into transaction response dto
	private List<TransactionResponseDto> transactionToResponseDto(List<Transaction> transactionList) {
		List<TransactionResponseDto> transactionResponse = transactionList.stream().map(transaction -> {
			TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
			transactionResponseDto.setAmount(transaction.getAmount());
			transactionResponseDto.setTransactionType(transaction.getTransactionType());
			transactionResponseDto.setDateTime(transaction.getDateTime());
			return transactionResponseDto;
		}).collect(Collectors.toList());
		return transactionResponse;
	}

	// This function converts transactionResponseDto to PaginationResponse
	private TransactionPaginationResponse transactionResponseToPagination(
			List<TransactionResponseDto> transactionResponse, int pageNumber, int pageSize) {
		
		long totalElements = transactionResponse.size();
		int totalPages = (int)Math.ceil((double) totalElements / pageSize);
		
		int start = (pageNumber - 1) * pageSize;
		int end = Math.min(start + pageSize, transactionResponse.size());
		List<TransactionResponseDto> paginatedList = transactionResponse.subList(start, end);
		
		
		TransactionPaginationResponse response = new TransactionPaginationResponse();
		response.setPageNo(pageNumber);
		response.setPageSize(pageSize);
		response.setTotalPages(totalPages);
		response.setTotalCount(totalElements);
		response.setTransactionList(paginatedList);
		return response;
	}

	@Override
	public TransactionPaginationResponse getStatement(int pageNumber, int pageSize, Long userId)
			throws TransactionApplicationException {
		List<Transaction> transactionList = isUserPersist(userId);
		List<TransactionResponseDto> transactionResponse = transactionToResponseDto(transactionList);
		return transactionResponseToPagination(transactionResponse, pageNumber, pageSize);
	}

	@Override
	public TransactionPaginationResponse getMonthlyStatement(int pageNumber, int pageSize, Long userId, int monthId, int year)
			throws TransactionApplicationException {
		List<Transaction> transactionList = isUserPersist(userId);
		List<Transaction> filteredTransaction = transactionList.stream().filter(transaction -> {
			return transaction.getDateTime().getMonthValue() == monthId && transaction.getDateTime().getYear() == year;
		}).collect(Collectors.toList());
		List<TransactionResponseDto> transactionResponse = transactionToResponseDto(filteredTransaction);
		return transactionResponseToPagination(transactionResponse, pageNumber, pageSize);
	}

	@Override
	public TransactionPaginationResponse getQuaterlyStatement(int pageNumber, int pageSize, Long userId, int quater, int year)
			throws TransactionApplicationException {
		List<Transaction> transactionList = isUserPersist(userId);
		List<Transaction> filteredTransaction = transactionList.stream().filter(transaction -> {
			return getQuater(transaction.getDateTime()) == quater && transaction.getDateTime().getYear() == year;
		}).collect(Collectors.toList());
		List<TransactionResponseDto> transactionResponse = transactionToResponseDto(filteredTransaction);
		return transactionResponseToPagination(transactionResponse, pageNumber, pageSize);
	}

	private static int getQuater(LocalDateTime date) {
		int month = date.getMonthValue();
		return (month - 1) / 3 + 1;
	}

	@Override
	public TransactionPaginationResponse getYearlyStatement(int pageNumber, int pageSize, Long userId, int year)
			throws TransactionApplicationException {
		List<Transaction> transactionList = isUserPersist(userId);
		List<Transaction> filteredTransaction = transactionList.stream().filter(transaction -> {
			return transaction.getDateTime().getYear() == year;
		}).collect(Collectors.toList());
		List<TransactionResponseDto> transactionResponse = transactionToResponseDto(filteredTransaction);
		return transactionResponseToPagination(transactionResponse, pageNumber, pageSize);
	}

}
