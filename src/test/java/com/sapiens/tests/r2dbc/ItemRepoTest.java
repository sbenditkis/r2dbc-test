package com.sapiens.tests.r2dbc;

import com.sapiens.tests.r2dbc.model.Item;
import com.sapiens.tests.r2dbc.repo.ItemRepo;
import com.sapiens.tests.r2dbc.repo.PersonRepo;
import org.jodah.concurrentunit.Waiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Config.class)
@ActiveProfiles("PG")
//@ActiveProfiles("MSSQL")

public class ItemRepoTest {
    @Autowired
    ItemRepo itemRepo;

    @Test
    public void testDuplicateData() throws Throwable {
        Waiter waiter = new Waiter();
        Long count = itemRepo.count().block();
        long now = System.currentTimeMillis();
        itemRepo.findAll().map(item-> {
            item.setId(null);
            return item;
        }).flatMap(itemRepo::save).subscribe(item-> {
            waiter.resume();
        });
        waiter.await(100000, count.intValue());
        System.out.println("copied "+count+", "+(System.currentTimeMillis()-now));
    }
}
