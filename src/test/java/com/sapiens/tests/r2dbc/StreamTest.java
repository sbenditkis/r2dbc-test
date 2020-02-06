package com.sapiens.tests.r2dbc;

import org.jodah.concurrentunit.Waiter;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StreamTest {
    final WebClient client = WebClient.builder().baseUrl("http://localhost:7777").build();

    @Test
    public void testStream() throws Throwable {
        Waiter waiter = new Waiter();
        Flux<Long> eventStream = client.get().uri("/counter/stream").retrieve().bodyToFlux(Long.class);
        eventStream.subscribe(
                content -> {
                    System.out.println(content.toString());
                    waiter.resume();
                },
                error -> System.err.println(error),
                () -> System.out.println("ENDED!"));
                waiter.await(10000, 5);

    }
}
