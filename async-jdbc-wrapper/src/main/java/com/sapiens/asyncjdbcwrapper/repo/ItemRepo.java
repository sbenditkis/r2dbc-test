package com.sapiens.asyncjdbcwrapper.repo;

import com.sapiens.asyncjdbcwrapper.jdbc.DataSourceProvider;
import com.sapiens.asyncjdbcwrapper.jdbc.ResultSetContext;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ItemRepo {

    @Resource
    private DataSourceProvider dataSourceProvider;

    String query = "select * from items";

    private Scheduler scheduler = Schedulers.newBoundedElastic(100, Integer.MAX_VALUE, "myThreads");

    public Flux<String> findAllItems() {
        return Mono.defer(() -> {
            try {
                Connection con = dataSourceProvider.getDataSource().getConnection();
                ResultSet rs = con.createStatement().executeQuery(query);
                return Mono.just(new ResultSetContext(con, rs, 20));
            } catch (SQLException ex) {
                return Mono.error(ex);
            }
        }).<String>flatMapMany(rsc -> Flux.create(sink -> {
            Flux.generate(() -> 0, (chunk, s) -> {
                if(rsc.isFinishedRead()) {
                    s.complete();
                } else {
                    s.next(chunk);
                }
                return chunk + 1;
            }).subscribe(chunk-> {
                for (int i = 0; i < rsc.getChunkSize(); i++) {
                    try {
                        ResultSet rs = rsc.getRs();
                        if (rs.next()) {
                            String data = rs.getString("data");
                            sink.next(data);
                        } else {
                            rs.close();
                            rsc.getConnection().close();
                            sink.complete();
                            rsc.setFinishedRead(true);
                            break;
                        }
                    } catch (SQLException ex) {
                        try {
                            rsc.getRs().close();
                            rsc.getConnection().close();
                            sink.error(ex);
                        } catch (SQLException ex2) {
                            ex2.printStackTrace();
//                    sink.error(ex);
                            sink.error(ex2);
                        }
                    }
                }
            });
        })).subscribeOn(scheduler);
    }
}
