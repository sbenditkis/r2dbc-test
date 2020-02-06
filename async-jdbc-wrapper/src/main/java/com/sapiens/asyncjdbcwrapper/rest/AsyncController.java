package com.sapiens.asyncjdbcwrapper.rest;

import com.sapiens.asyncjdbcwrapper.repo.ItemRepo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@RestController
@RequestMapping("/jdbcwrap")
public class AsyncController {

    @Resource
    private ItemRepo itemRepo;

    @GetMapping(path="/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<String> dataStream() {
        return itemRepo.findAllItems();
    }

}
