package com.ars.reactiveapi;

import com.ars.reactiveapi.entity.Transaction;
import com.ars.reactiveapi.repository.TransactionRepository;
import com.ars.reactiveapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.Instant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
}
