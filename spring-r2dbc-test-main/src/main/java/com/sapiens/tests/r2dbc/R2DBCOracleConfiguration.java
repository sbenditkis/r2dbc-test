package com.sapiens.tests.r2dbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sapiens.bdms.R2DBCOnJdbcConnectionFactory;
import com.sapiens.bdms.R2DBCOnJdbcDialectProvider;
import com.sapiens.tests.r2dbc.repo.*;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("JDBC-ORACLE")
public class R2DBCOracleConfiguration extends AbstractR2dbcConfiguration {

    @Bean
    public ConnectionFactory connectionFactory()  {
        ConnectionFactory cf = new R2DBCOnJdbcConnectionFactory(dataSource());
//        ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(cf)
//                .maxIdleTime(Duration.ofMillis(1000))
//                .maxSize(50)
//                .build();
//        cf = new ConnectionPool(configuration);
        return cf;
    }

    @Bean
    DataSource dataSource() {
        OracleDataSource ods;
        try {
            ods = new OracleDataSource();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ods.setURL("jdbc:oracle:thin:@localhost:1522/XEPDB1");
        ods.setUser("dec_test1");
        ods.setPassword("decision");

        return ods;
    };

    @Bean
    R2DBCOnJdbcDialectProvider r2DBCOnJdbcDialectProvider() {
        return new R2DBCOnJdbcDialectProvider();
    }

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new CLOBToMapConverter(objectMapper()));
        converters.add(new MapToStringConverter(objectMapper()));
        return new R2dbcCustomConversions(getStoreConversions(), converters);
    }

    @Bean
    NamingStrategy namingStrategy() {
        return new NamingStrategy() {
            @Override
            public String getColumnName(RelationalPersistentProperty property) {
                return property.getName().toUpperCase();
            }
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
