package com.sapiens.tests.r2dbc.repo;

import com.sapiens.tests.r2dbc.model.Person;
import reactor.core.publisher.Flux;

public interface PersonRepoCustom {
    Flux<Person> findByX(String x);
}
