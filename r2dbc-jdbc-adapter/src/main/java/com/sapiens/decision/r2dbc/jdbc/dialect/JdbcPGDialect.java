package com.sapiens.decision.r2dbc.jdbc.dialect;

import org.springframework.data.r2dbc.dialect.BindMarkersFactory;
import org.springframework.data.r2dbc.dialect.PostgresDialect;

public class JdbcPGDialect extends PostgresDialect {
    public static final JdbcPGDialect INSTANCE = new JdbcPGDialect();
    private static final BindMarkersFactory ANONYMOUS = BindMarkersFactory.anonymous("?");

    @Override
    public BindMarkersFactory getBindMarkersFactory() {
        return ANONYMOUS;
    }
}
