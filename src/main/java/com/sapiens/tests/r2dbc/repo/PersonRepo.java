package com.sapiens.tests.r2dbc.repo;

import com.sapiens.tests.r2dbc.model.Person;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PersonRepo extends ReactiveCrudRepository<Person, Integer> {
    @Query("select p from Person p where p.data->>'x' = :x")
    Flux<Person> findByX(String x);
}
