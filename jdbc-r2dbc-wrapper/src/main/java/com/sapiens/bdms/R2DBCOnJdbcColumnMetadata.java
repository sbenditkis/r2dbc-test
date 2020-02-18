package com.sapiens.bdms;

import io.r2dbc.spi.ColumnMetadata;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class R2DBCOnJdbcColumnMetadata implements ColumnMetadata {

    private final ResultSetMetaData resultSetMetaData;
    private final int columnIndex;

    public R2DBCOnJdbcColumnMetadata(ResultSetMetaData resultSetMetaData, int columnIndex) {
        this.resultSetMetaData = resultSetMetaData;
        this.columnIndex = columnIndex;
    }

    @Override
    public String getName() {
        try {
            return resultSetMetaData.getColumnName(columnIndex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
