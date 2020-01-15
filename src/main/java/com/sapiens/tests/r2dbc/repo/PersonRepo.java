package com.sapiens.tests.r2dbc.repo;

import com.sapiens.tests.r2dbc.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepo extends ReactiveCrudRepository<Person, Integer> {
}
