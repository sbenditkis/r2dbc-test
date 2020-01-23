package com.sapiens.tests.r2dbc;

import com.sapiens.tests.r2dbc.model.Person;
import com.sapiens.tests.r2dbc.repo.PersonRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Config.class)
@ActiveProfiles("PG")
//@ActiveProfiles("MSSQL")
public class PersonRepoTest {

    @Autowired
    PersonRepo personRepo;

//    @Autowired
//    DatabaseClient client;

    /*
    @Before
    public void setUp() {
        Hooks.onOperatorDebug();

        List<String> statements = Arrays.asList(//
                "DROP TABLE IF EXISTS person;",
                "CREATE table person (id INT AUTO_INCREMENT NOT NULL, name VARCHAR2, data jsonb);");

        statements.forEach(it -> client.execute(it) //
                .fetch() //
                .rowsUpdated() //
                .as(StepVerifier::create) //
                .expectNextCount(1) //
                .verifyComplete());
    }
    */

    @Test
    public void testCreate() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("x", "xxx");

        Person p = new Person(null, "AAA", data);
        personRepo.save(p).block();

        Person p1 = personRepo.findByX("xxx").blockFirst();
        Assert.assertEquals(p.getName(), p1.getName());
    }
}
