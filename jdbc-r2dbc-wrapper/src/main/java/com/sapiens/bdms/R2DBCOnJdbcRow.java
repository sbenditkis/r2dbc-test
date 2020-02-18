package com.sapiens.bdms;

import io.r2dbc.spi.Row;

import java.sql.ResultSet;
import java.sql.SQLException;

public class R2DBCOnJdbcRow implements Row {
    private ResultSet resultSet;
    private R2DBCOnJdbcRowMetadata metadata;

    public R2DBCOnJdbcRow(ResultSet resultSet, R2DBCOnJdbcRowMetadata metadata) {
        this.resultSet = resultSet;
        this.metadata = metadata;
    }

    @Override
    public <T> T get(int index, Class<T> type) {
        try {
            return resultSet.getObject(index, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T get(String name, Class<T> type) {
        return get(metadata.getColumnIndex(name), type);
    }

    public Object get(String name) {
        try {
            return resultSet.getObject(metadata.getColumnIndex(name));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
