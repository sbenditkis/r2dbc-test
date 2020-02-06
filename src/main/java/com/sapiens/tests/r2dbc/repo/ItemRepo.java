package com.sapiens.tests.r2dbc.repo;

import com.sapiens.tests.r2dbc.model.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepo extends ReactiveCrudRepository<Item, Integer> {
}
