package com.sapiens.tests.r2dbc.repo.mssql;

import com.sapiens.tests.r2dbc.model.Person;
import com.sapiens.tests.r2dbc.repo.PersonRepoCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("personRepoImpl")
@Profile("MSSQL")
public class PersonRepoMssqlImpl implements PersonRepoCustom {

    @Autowired
    private DatabaseClient databaseClient;

    @Override
    public Flux<Person> findByX(String x) {
        return databaseClient.execute("select p.* from Person p where JSON_VALUE(p.data, '$.x') = :x")
                        .bind("x", x)
                .as(Person.class).fetch().all();
    }
}
