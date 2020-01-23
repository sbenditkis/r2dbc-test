package com.sapiens.tests.r2dbc.repo;

import com.sapiens.tests.r2dbc.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

public class PersonRepoImpl implements PersonRepoCustom {

    @Autowired
    private DatabaseClient databaseClient;

    @Override
    public Flux<Person> findByX(String x) {
        return databaseClient.execute("select p.* from Person p where p.data->>'x' = $1")
                        .bind("$1", x)
                .as(Person.class).fetch().all();
    }
}
