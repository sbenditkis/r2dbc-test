package com.sapiens.bdms;

import io.r2dbc.spi.ConnectionFactoryMetadata;

public class R2DBCOnJdbcConnectionMetadata implements ConnectionFactoryMetadata {
    @Override
    public String getName() {
        return "PostgreSQL";
    }
}
