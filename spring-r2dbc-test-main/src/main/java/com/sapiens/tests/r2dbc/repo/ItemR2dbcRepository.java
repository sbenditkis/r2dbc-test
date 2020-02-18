package com.sapiens.tests.r2dbc.repo;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class ItemR2dbcRepository {

    @Resource
    ConnectionFactory connectionFactory;

    public Flux<String> findAllData() {
        return Mono.from(connectionFactory.create())
                .flatMapMany(conn -> Mono.from(conn.createStatement("select * from items").execute())
                        .flatMapMany(result -> result.map((row, meta) ->
                            row.get("data", String.class)
                        ))
                        .doFinally(st ->
                            Mono.from(conn.close()).subscribe()
                        ))
                ;
    }
}
