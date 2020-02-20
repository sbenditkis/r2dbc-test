package com.sapiens.tests.r2dbc.repo;

import com.sapiens.tests.r2dbc.model.Person;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepo extends ReactiveCrudRepository<Person, Integer>, PersonRepoCustom {
    @Query("select * from Person where name=:name")
    Flux<Person> getByName(String name);
}
