package com.sapiens.tests.r2dbc.controller;

import com.sapiens.tests.r2dbc.repo.ItemR2dbcRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

@RestController
@RequestMapping("/items2")
public class ItemsController2 {
    @Resource
    private ItemR2dbcRepository itemRepositoty;

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<String> getAllItems() {
        return itemRepositoty.findAllData();
    }
}
