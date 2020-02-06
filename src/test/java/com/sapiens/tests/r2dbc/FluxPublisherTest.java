package com.sapiens.tests.r2dbc;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class FluxPublisherTest {

    @Test
    public void testFluxPublisher() {
        AtomicInteger count = new AtomicInteger();
        Mono.just(Arrays.asList(10,20,30)).flatMapMany(list->
        Flux.generate(() -> 0, (state, sink)->{
            if(state<list.size()) sink.next(list.get(state));
            else sink.complete();
            return state+1;
        })).subscribe(System.out::println);
    }
}
