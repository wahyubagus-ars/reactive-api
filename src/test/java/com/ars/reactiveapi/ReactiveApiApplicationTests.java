package com.ars.reactiveapi;

import com.ars.reactiveapi.dto.SubmitTransactionReq;
import com.ars.reactiveapi.entity.Transaction;
import com.ars.reactiveapi.repository.TransactionRepository;
import com.ars.reactiveapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReactiveApiApplicationTests {

	@Test
	public void testBackpressureHandling() {
		TransactionRepository mockRepository = mock(TransactionRepository.class);

		Flux<Transaction> mockFlux = Flux.range(1, 15)
				.map(i -> Transaction.builder().build());

		when(mockRepository.findAll()).thenReturn(mockFlux);
		TransactionService transactionService = new TransactionService(mockRepository);

		StepVerifier.create(transactionService.getTransactionData())
				.expectSubscription()
				.thenAwait(Duration.ofSeconds(1)) // Wait for a bit to observe behavior
				.expectNextCount(5) // Expect the first 10 items
				.thenAwait(Duration.ofSeconds(1)) // Wait to allow more items to be processed
				.expectNextCount(5) // Expect remaining items, if all are processed
				.thenAwait(Duration.ofSeconds(1)) // Wait to allow more items to be processed
				.expectNextCount(5) // Expect remaining items, if all are processed
				.verifyComplete(); // Complete the verification
	}

	@Test
	public void testSubmitTransaction() {
		TransactionRepository mockRepository = mock(TransactionRepository.class);
		SubmitTransactionReq request = SubmitTransactionReq.builder()
				.userId(1L)
				.paymentMethod("QRIS_test")
				.build();

		when(mockRepository.save(any(Transaction.class)))
				.thenReturn(Mono.just(Transaction.builder()
								.userId(1L)
								.paymentMethod("QRIS_test")
						.build()));
		TransactionService transactionService = new TransactionService(mockRepository);

		Mono<Transaction> result = transactionService.submitTransaction(Mono.just(request));

		StepVerifier.create(result)
				.expectNextMatches(savedTransaction -> savedTransaction.getUserId().equals(1L) &&
						"QRIS_test".equalsIgnoreCase(savedTransaction.getPaymentMethod()))
				.verifyComplete();

		verify(mockRepository, Mockito.times(1)).save(Mockito.any(Transaction.class));
	}
}
