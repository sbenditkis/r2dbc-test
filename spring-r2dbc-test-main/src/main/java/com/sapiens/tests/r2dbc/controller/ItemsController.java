package com.sapiens.tests.r2dbc.controller;

import com.sapiens.tests.r2dbc.model.Item;
import com.sapiens.tests.r2dbc.repo.ItemRepo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("/items")
public class ItemsController {
    @Resource
    private ItemRepo itemRepo;

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Item> getAllItems() {
        return itemRepo.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Item> getItemById(@PathVariable("id") int id) {
        return itemRepo.findById(id);
    }

    @PostMapping("/")
    public Mono<Item> create(@RequestBody Item item) {
        return itemRepo.save(item);
    }
}
