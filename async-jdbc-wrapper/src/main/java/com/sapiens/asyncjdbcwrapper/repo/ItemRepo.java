package com.sapiens.asyncjdbcwrapper.repo;

import com.sapiens.asyncjdbcwrapper.jdbc.ResultSetContext;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ItemRepo {
    String query = "select data from items";

    public Flux<String> findAllItems() {
        return Mono.<ResultSetContext>create(sink -> {
            try {
                Connection con = DriverManager.
                        getConnection("jdbc:postgresql:dec_test1"
                                , "postgres", "postgres");
                ResultSet rs = con.createStatement().executeQuery(query);
                sink.success(new ResultSetContext(con, rs));
            } catch (SQLException ex) {
                sink.error(ex);
            }
        }).flatMapMany(resultSetContext -> Flux.generate(()->resultSetContext, (rsc, sink) -> {
            try {
                ResultSet rs = rsc.getRs();
                if (rs.next()) {
                    String data = rs.getString("data");
                    sink.next(data);
                } else {
                    rs.close();
                    rsc.getConnection().close();
                    sink.complete();
                }
            } catch (SQLException ex) {
                sink.error(ex);
            }
            return rsc;
        }));
    }
}
