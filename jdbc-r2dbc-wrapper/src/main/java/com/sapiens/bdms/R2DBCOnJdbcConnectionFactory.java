package com.sapiens.bdms;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;

public class R2DBCOnJdbcConnectionFactory implements ConnectionFactory {

    private DataSource dataSource;

    @Autowired
    public R2DBCOnJdbcConnectionFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Publisher<? extends Connection> create() {
        return Mono.defer(()->Mono.just(new R2DBCOnJdbcConnection(dataSource)));
    }

    @Override
    public ConnectionFactoryMetadata getMetadata() {
        return new R2DBCOnJdbcConnectionMetadata();
    }
}
