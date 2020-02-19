package com.sapiens.bdms;

import org.springframework.data.r2dbc.dialect.BindMarkersFactory;
import org.springframework.data.r2dbc.dialect.PostgresDialect;

public class JdbcPGDialect extends PostgresDialect {
    private static final BindMarkersFactory ANONYMOUS = BindMarkersFactory.anonymous("?");

    @Override
    public BindMarkersFactory getBindMarkersFactory() {
        return ANONYMOUS;
    }
}
