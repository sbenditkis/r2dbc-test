package com.sapiens.bdms;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class R2DBCOnJdbcConnectionFactory implements ConnectionFactory {

    private DataSourceProvider dataSourceProvider;

    @Autowired
    public R2DBCOnJdbcConnectionFactory(DataSourceProvider dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }

    @Override
    public Publisher<? extends Connection> create() {
        return Mono.defer(()->Mono.just(new R2DBCOnJdbcConnection(dataSourceProvider)));
    }

    @Override
    public ConnectionFactoryMetadata getMetadata() {
        return new R2DBCOnJdbcConnectionMetadata();
    }
}
