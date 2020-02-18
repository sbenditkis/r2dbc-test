package com.sapiens.tests.r2dbc.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/counter")
public class CounterController {

    @GetMapping(path = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> counterStream() {
        System.out.println("counterStream()");
        Flux<Long> flux = Flux.generate(() -> 1000l, (i, s) -> {
            s.next(i);
            return i + 1;
        });
        return flux.delaySequence(Duration.ofSeconds(1));
    }

    @GetMapping(path = "/list")
    public Flux<Long> counterList() {
        System.out.println("counterList()");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Flux.fromArray(new Long[] {1l, 2l, 3l});
    }

}
