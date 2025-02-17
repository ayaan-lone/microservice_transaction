//package com.onlineBanking.transaction.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDateTime;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import com.onlineBanking.transaction.dao.TransactionRepository;
//import com.onlineBanking.transaction.entity.Transaction;
//import com.onlineBanking.transaction.request.TransactionDetailsRequestDto;
//import com.onlineBanking.transaction.service.impl.TransactionServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class TransactionServiceImplTest {
//
//    @InjectMocks
//    private TransactionServiceImpl transactionServiceImpl;
//
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    private TransactionDetailsRequestDto transactionDetailsDto;
//
//    @BeforeEach
//    public void setUp() {
//        transactionDetailsDto = new TransactionDetailsRequestDto();
//        transactionDetailsDto.setUserId(1L);
//        transactionDetailsDto.setAmount(100);
//        transactionDetailsDto.setTransactionType("DEBIT");
//
//        transactionServiceImpl.accountBalanceUpdateUrl = "http://localhost:8080/updateBalance";
//    }
//
//    @Test
//    public void testCreateUserTransactions_Success() {
//        Transaction transaction = new Transaction();
//        transaction.setId(1L);
//        transaction.setUserId(transactionDetailsDto.getUserId());
//        transaction.setAmount(transactionDetailsDto.getAmount());
//        transaction.setTransactionType(transactionDetailsDto.getTransactionType());
//        transaction.setDateTime(LocalDateTime.now());
//
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<TransactionDetailsRequestDto> requestEntity = new HttpEntity<>(transactionDetailsDto, headers);
//
//        ResponseEntity<String> responseEntity = ResponseEntity.ok("Balance updated successfully");
//
//        when(restTemplate.exchange(
//                eq("http://localhost:8080/updateBalance"),
//                eq(HttpMethod.POST),
//                eq(requestEntity),
//                eq(String.class)
//        )).thenReturn(responseEntity);
//
//        String response = transactionServiceImpl.createUserTransactions(transactionDetailsDto);
//
//        assertNotNull(response);
//        assertEquals(responseEntity.toString(), response);
//
//        verify(transactionRepository).save(any(Transaction.class));
//        verify(restTemplate).exchange(
//                eq("http://localhost:8080/updateBalance"),
//                eq(HttpMethod.POST),
//                eq(requestEntity),
//                eq(String.class)
//        );
//    }
//}
package com.onlineBanking.transaction.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.onlineBanking.transaction.entity.TransactionType;
import com.onlineBanking.transaction.request.TransactionDetailsRequestDto;
import com.onlineBanking.transaction.service.impl.TransactionServiceImpl;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class TransactionServiceTests {

    @Autowired
    private TransactionServiceImpl transactionService;

    @Test
    public void testSimultaneousTransactions() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                try {
                    TransactionDetailsRequestDto requestDto = new TransactionDetailsRequestDto();
                    requestDto.setUserId(10);
                    requestDto.setAmount(100.0);
                    requestDto.setTransactionType(TransactionType.DEBIT);
                    transactionService.transactionDetails(requestDto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }
    }
}

