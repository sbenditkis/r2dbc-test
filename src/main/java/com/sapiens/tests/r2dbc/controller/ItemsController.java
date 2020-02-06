package com.sapiens.tests.r2dbc.controller;

import com.sapiens.tests.r2dbc.model.Item;
import com.sapiens.tests.r2dbc.repo.ItemRepo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

@RestController
@RequestMapping("/items")
public class ItemsController {
    @Resource
    private ItemRepo itemRepo;

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<String> getAllItems() {
        return itemRepo.findAll().map(Item::getData);
    }
}
