package com.sapiens.tests.r2dbc.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class CounterClient {
    public static void main(String[] args) throws Exception {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:7777").build();
        Flux<Long> eventStream = webClient.get().uri("/counter/stream").retrieve().bodyToFlux(Long.class);
//        while(true) {
//            Long e = eventStream.next().block();
//            System.out.println(e);
//        }

        eventStream.subscribe(
                content -> System.out.println(content.toString()),
                error -> System.err.println(error),
                () -> System.out.println("ENDED!"));

    }
}
