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
        Mono.just(Arrays.asList(10, 20, 30)).flatMapMany(list ->
                Flux.<Integer>create(sink -> {
                    Flux.generate(() -> 0, (ind, s) -> {
                        if(ind<list.size()) {
                            s.next(list.get(ind));
                        } else {
                            s.complete();
                        }
                        return ind + 1;
                    }).<Integer>subscribe(ind -> {
                                for (int i = 0; i < 10; i++) {
                                    sink.next(((Integer)ind) + i);
                                }
                            }
                    );
                })).subscribe(System.out::println);
    }
}
