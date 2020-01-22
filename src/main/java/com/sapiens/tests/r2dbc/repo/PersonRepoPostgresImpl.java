package com.sapiens.tests.r2dbc.repo;

import com.sapiens.tests.r2dbc.model.Person;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class PersonRepoPostgresImpl implements PersonRepoCustom {

    @Autowired
    ConnectionFactory connectionFactory;

    @Override
    public Flux<Person> findByX(String x) {
        return Flux.from(connectionFactory.create()).flatMap(
                c-> Flux.from(c.createStatement("select p.* from Person p where p.data->>'x' = $1")
                        .bind("$1", x)
                        .execute()).doFinally(st->c.close()));
    }
}
