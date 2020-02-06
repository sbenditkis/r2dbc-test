package com.sapiens.tests.r2dbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapiens.tests.r2dbc.repo.JsonToMapConverter;
import com.sapiens.tests.r2dbc.repo.JsonToStringConverter;
import com.sapiens.tests.r2dbc.repo.MapToJsonConverter;
import com.sapiens.tests.r2dbc.repo.StringToJsonConverter;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("PG")
public class R2DBCPostgresConfiguration extends AbstractR2dbcConfiguration {

    public ConnectionFactory connectionFactory() {
//        return new H2ConnectionFactory(
//                H2ConnectionConfiguration.builder()
//                        .url("mem:testdb;DB_CLOSE_DELAY=-1;")
//                        .username("sa")
//                        .build()
//        );
    return new PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                    .host("localhost")
                    .database("dec_test1")
                    .username("postgres")
                    .password("postgres")
                    .build()
    );
    }

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new JsonToMapConverter(objectMapper()));
        converters.add(new MapToJsonConverter(objectMapper()));
        converters.add(new StringToJsonConverter());
        converters.add(new JsonToStringConverter());
        return new R2dbcCustomConversions(getStoreConversions(), converters);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
