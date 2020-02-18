package com.sapiens.bdms;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class DataSourceProvider {
    private ComboPooledDataSource cpds;

    @PostConstruct
    public void init() throws Exception {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:postgresql:dec_test1" );
        cpds.setUser("postgres");
        cpds.setPassword("postgres");
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(50);
//        cpds.setMaxStatements(0);
    }

    public DataSource getDataSource() {
        return cpds;
    }
}
