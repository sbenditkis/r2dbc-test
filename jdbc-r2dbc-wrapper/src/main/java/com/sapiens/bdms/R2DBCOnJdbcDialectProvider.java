package com.sapiens.bdms;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;

import java.util.Optional;

public class R2DBCOnJdbcDialectProvider implements DialectResolver.R2dbcDialectProvider {
    public static JdbcPGDialect jdbcPGDialect = new JdbcPGDialect();
    @Override
    public Optional<R2dbcDialect> getDialect(ConnectionFactory connectionFactory) {
        String dialectName = connectionFactory.getMetadata().getName();
        if(dialectName.equalsIgnoreCase("jdbcpg")) {
            return Optional.of(jdbcPGDialect);
        }
        return Optional.empty();
    }
}
