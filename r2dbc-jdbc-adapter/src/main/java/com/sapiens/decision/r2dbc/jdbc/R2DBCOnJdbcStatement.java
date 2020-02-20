package com.sapiens.decision.r2dbc.jdbc;

import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class R2DBCOnJdbcStatement implements Statement {

    private final PreparedStatement preparedStatement;
    private String sql;
    private R2DBCOnJdbcConnection connection;

    public R2DBCOnJdbcStatement(String sql, R2DBCOnJdbcConnection connection) {
        try {
            preparedStatement = connection.getJdbcConnection().prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.sql = sql;
        this.connection = connection;
    }

    @Override
    public Statement add() {
        return this;
    }

    @Override
    public Statement bind(int index, Object value) {
        try {
            if(value instanceof LocalDateTime) {
                value = java.sql.Timestamp.valueOf((LocalDateTime) value);
            }
            preparedStatement.setObject(index+1, value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public Statement bind(String name, Object value) {
        return this;
    }

    @Override
    public Statement bindNull(int index, Class<?> type) {
        try {
            preparedStatement.setObject(index, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public Statement bindNull(String name, Class<?> type) {
        return this;
    }

    @Override
    public Publisher<? extends Result> execute() {
        return Mono.defer(() -> {
            try {
                preparedStatement.execute();
                ResultSet rs = preparedStatement.getResultSet();
                R2DBCOnJdbcResult result = new R2DBCOnJdbcResult(rs);
                return Mono.just(result);
            } catch (SQLException ex) {
                return Mono.error(ex);
            }
        });
    }
}
