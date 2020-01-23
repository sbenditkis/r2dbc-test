package com.sapiens.tests.r2dbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapiens.tests.r2dbc.repo.JsonToMapConverter;
import com.sapiens.tests.r2dbc.repo.MapToJsonConverter;
import com.sapiens.tests.r2dbc.repo.MapToStringConverter;
import com.sapiens.tests.r2dbc.repo.StringToMapConverter;
import io.r2dbc.mssql.MssqlConnectionConfiguration;
import io.r2dbc.mssql.MssqlConnectionFactory;
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
@Profile("MSSQL")
public class R2DBCMssqlConfiguration extends AbstractR2dbcConfiguration {

    public ConnectionFactory connectionFactory() {
        return new MssqlConnectionFactory(
                MssqlConnectionConfiguration.builder()
                        .host("localhost")
                        .database("dec_test1")
                        .username("decision")
                        .password("decision")
                        .build()
        );
    }

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new StringToMapConverter(objectMapper()));
        converters.add(new MapToStringConverter(objectMapper()));
        return new R2dbcCustomConversions(getStoreConversions(), converters);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
