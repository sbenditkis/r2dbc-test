package com.sapiens.tests.r2dbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapiens.bdms.DataSourceProvider;
import com.sapiens.bdms.R2DBCOnJdbcConnectionFactory;
import com.sapiens.tests.r2dbc.repo.*;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("JDBC-PG")
public class R2DBCJdbcConfiguration extends AbstractR2dbcConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory cf = new R2DBCOnJdbcConnectionFactory(dataSourceProvider());
//        ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(cf)
//                .maxIdleTime(Duration.ofMillis(1000))
//                .maxSize(50)
//                .build();
//        return new ConnectionPool(configuration);
        return cf;
    }

    @Bean
    DataSourceProvider dataSourceProvider() {
        return new DataSourceProvider();
    };

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new JsonToMapConverter(objectMapper()));
        converters.add(new MapToJsonConverter(objectMapper()));
        converters.add(new StringToJsonConverter());
        converters.add(new JsonToStringConverter());
        converters.add(new StringToPGObjectConverter());
        converters.add(new PGObjectToStringConverter());
        return new R2dbcCustomConversions(getStoreConversions(), converters);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
