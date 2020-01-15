package com.sapiens.tests.r2dbc;

import com.sapiens.tests.r2dbc.model.Person;
import com.sapiens.tests.r2dbc.repo.PersonRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Config.class)
public class PersonRepoTest {

    @Autowired
    PersonRepo personRepo;

    @Autowired
    DatabaseClient client;

    @Before
    public void setUp() {
        Hooks.onOperatorDebug();

        List<String> statements = Arrays.asList(//
                "DROP TABLE IF EXISTS person;",
                "CREATE table person (id INT AUTO_INCREMENT NOT NULL, name VARCHAR2);");

        statements.forEach(it -> client.execute(it) //
                .fetch() //
                .rowsUpdated() //
                .as(StepVerifier::create) //
                .expectNextCount(1) //
                .verifyComplete());
    }

    @Test
    public void testCreate() {
        Person p = new Person(null,"AAA");
        personRepo.save(p).subscribe();

        personRepo.findById(1).as(StepVerifier::create).expectNextCount(1).verifyComplete();
    }
}
