package com.onlineBanking.transaction.service.impl;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
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

import com.onlineBanking.transaction.client.AccountClientHandler;
import com.onlineBanking.transaction.dao.TransactionRepository;
import com.onlineBanking.transaction.entity.Transaction;
import com.onlineBanking.transaction.entity.TransactionType;
import com.onlineBanking.transaction.exception.DateRangeException;
import com.onlineBanking.transaction.exception.InsufficientFundsException;
import com.onlineBanking.transaction.exception.InvalidAmountException;
import com.onlineBanking.transaction.exception.TransactionApplicationException;
import com.onlineBanking.transaction.request.TransactionDetailsRequestDto;
import com.onlineBanking.transaction.response.TransactionPaginationResponse;
import com.onlineBanking.transaction.response.TransactionResponseDto;
import com.onlineBanking.transaction.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final RestTemplate restTemplate;
	private final AccountClientHandler accountClientHandler;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository, RestTemplate restTemplate,
			AccountClientHandler accountClientHandler) {
		this.transactionRepository = transactionRepository;
		this.restTemplate = restTemplate;
		this.accountClientHandler = accountClientHandler;
	}

	@Value("${onlineBanking.user.url}")
	public String isUserVerifiedUrl;

	// Method to check whether the transaction type is valid or not
	private static void isValidTransaction(TransactionType transaction) throws TransactionApplicationException {
		List<TransactionType> validTransactions = Arrays.stream(TransactionType.values()).collect(Collectors.toList());

		if (!validTransactions.contains(transaction)) {
			throw new TransactionApplicationException(HttpStatus.BAD_REQUEST, "Transaction type is not valid");
		}
	}

	// function to handle all the debit transactions
	private String handleDebitTransaction(TransactionDetailsRequestDto transactionDetailsRequestDto)
			throws InsufficientFundsException {
		Double balance = accountClientHandler.getBalance(transactionDetailsRequestDto.getUserId());

		if (balance < transactionDetailsRequestDto.getAmount()) {
			throw new InsufficientFundsException();
		}

		return accountClientHandler.updateBalance(transactionDetailsRequestDto);
	}

	// function to handle all the credit transactions
	private String handleCreditTransactions(TransactionDetailsRequestDto transaDetailsRequestDto) {
		return accountClientHandler.updateBalance(transaDetailsRequestDto);
	}
	
	// Function to store the transactions in db
	private String createUserTransactions(TransactionDetailsRequestDto transactionDetailsRequestDto) {
		Transaction transaction = new Transaction();
		transaction.setUserId(transactionDetailsRequestDto.getUserId());
		transaction.setAmount(transactionDetailsRequestDto.getAmount());
		transaction.setTransactionType(transactionDetailsRequestDto.getTransactionType());
		transaction.setDateTime(LocalDateTime.now());
		transactionRepository.save(transaction);
		
		return "Transaction is saved in db";
	}
	
	
	// Function to validate that the amount should not be equal to zero
	private void checkAmount(Double amount) throws InvalidAmountException {
		if(amount<=0) {
			throw new InvalidAmountException();
		}
	}
	

	// Function to validate the transaction and update the balance
	@Override
	public String transactionDetails(TransactionDetailsRequestDto transactionDetialsRequestDto)
			throws TransactionApplicationException, InsufficientFundsException, InvalidAmountException {

		// Check whether the transactionType is correct or not
		isValidTransaction(transactionDetialsRequestDto.getTransactionType());
		
		// Check that amount should not be zero
		checkAmount(transactionDetialsRequestDto.getAmount());

		// First check whether the user exists or not and if user account is not deleted
		ResponseEntity<Boolean> isUserVerified = restTemplate.exchange(
				isUserVerifiedUrl + "/verify-user?userId=" + transactionDetialsRequestDto.getUserId(), HttpMethod.GET, null,
				Boolean.class);

		String response = transactionDetialsRequestDto.getTransactionType().equals(TransactionType.DEBIT)
				? handleDebitTransaction(transactionDetialsRequestDto)
				: handleCreditTransactions(transactionDetialsRequestDto);

		String transactionResponse = createUserTransactions(transactionDetialsRequestDto);
		
		return response + " and " + transactionResponse;
	}

	// Function to check whether the user is present or not
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
		int totalPages = (int) Math.ceil((double) totalElements / pageSize);

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

	// Function to get all the user's bank statements
	@Override
	public TransactionPaginationResponse getStatement(int pageNumber, int pageSize, Long userId)
			throws TransactionApplicationException {
		List<Transaction> transactionList = isUserPersist(userId);
		List<TransactionResponseDto> transactionResponse = transactionToResponseDto(transactionList);
		return transactionResponseToPagination(transactionResponse, pageNumber, pageSize);
	}

	// Function to convert Month Name to Month id
	private int convertMonthNameToId(String month) throws DateRangeException {
		// Check whether month name is valid or not
		List<String> validMonthName = Arrays.stream(Month.values()).map(Enum::name).map(String::toUpperCase)
				.collect(Collectors.toList());
		if (!validMonthName.contains(month)) {
			throw new DateRangeException("Month provided: " + month + " is not a valid month");
		}

		return Month.valueOf(month.toUpperCase()).getValue();
	}

	// Function to get user's monthly statements
	@Override
	public TransactionPaginationResponse getMonthlyStatement(int pageNumber, int pageSize, Long userId, String month)
			throws TransactionApplicationException, DateRangeException {
		List<Transaction> transactionList = isUserPersist(userId);
		int monthId = convertMonthNameToId(month);

		// If month provided is in greater than the current month throw this exception
		if (monthId > LocalDateTime.now().getMonthValue()) {
			throw new DateRangeException("Month name should be before current month or the current month itself");
		}

		List<Transaction> filteredTransaction = transactionList.stream().filter(transaction -> {
			return transaction.getDateTime().getMonthValue() == monthId
					&& transaction.getDateTime().getYear() == Year.now().getValue();
		}).collect(Collectors.toList());
		List<TransactionResponseDto> transactionResponse = transactionToResponseDto(filteredTransaction);
		return transactionResponseToPagination(transactionResponse, pageNumber, pageSize);
	}

	// Function to return the quater of year if the date is given
	private static int getQuater(LocalDateTime date) {
		int month = date.getMonthValue();
		return (month - 1) / 3 + 1;
	}

	// Function to get user's quaterly statements
	@Override
	public TransactionPaginationResponse getQuaterlyStatement(int pageNumber, int pageSize, Long userId, int quater)
			throws TransactionApplicationException, DateRangeException {
		List<Transaction> transactionList = isUserPersist(userId);

		// Check whether the quater is greater than current quater
		if (quater > getQuater(LocalDateTime.now())) {
			throw new DateRangeException("Provided quater: " + quater + " is greater than current quater");
		}

		List<Transaction> filteredTransaction = transactionList.stream().filter(transaction -> {
			return getQuater(transaction.getDateTime()) == quater
					&& transaction.getDateTime().getYear() == Year.now().getValue();
		}).collect(Collectors.toList());
		List<TransactionResponseDto> transactionResponse = transactionToResponseDto(filteredTransaction);
		return transactionResponseToPagination(transactionResponse, pageNumber, pageSize);
	}

	// Function to get yearly statements
	@Override
	public TransactionPaginationResponse getYearlyStatement(int pageNumber, int pageSize, Long userId, int year)
			throws TransactionApplicationException, DateRangeException {
		List<Transaction> transactionList = isUserPersist(userId);

		// Check whether given year is greater than current year
		if (year > LocalDateTime.now().getYear()) {
			throw new DateRangeException("Provided year is greater than the current year");
		}

		List<Transaction> filteredTransaction = transactionList.stream().filter(transaction -> {
			return transaction.getDateTime().getYear() == year;
		}).collect(Collectors.toList());
		List<TransactionResponseDto> transactionResponse = transactionToResponseDto(filteredTransaction);
		return transactionResponseToPagination(transactionResponse, pageNumber, pageSize);
	}

}
