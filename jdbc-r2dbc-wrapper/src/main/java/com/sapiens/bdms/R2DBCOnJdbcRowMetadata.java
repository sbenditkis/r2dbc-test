package com.sapiens.bdms;

import io.r2dbc.spi.ColumnMetadata;
import io.r2dbc.spi.RowMetadata;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class R2DBCOnJdbcRowMetadata implements RowMetadata {

    private ResultSetMetaData resultSetMetaData;
    private HashMap<String, Integer> columnNamesIndices = new HashMap<>();

    public R2DBCOnJdbcRowMetadata(ResultSetMetaData resultSetMetaData) throws SQLException {
        this.resultSetMetaData = resultSetMetaData;
        for(int i=1; i <= resultSetMetaData.getColumnCount(); i++) {
            String columnName = resultSetMetaData.getColumnName(i);
            columnNamesIndices.put(columnName, i);
        }
    }

    @Override
    public ColumnMetadata getColumnMetadata(int index) {
        return new R2DBCOnJdbcColumnMetadata(resultSetMetaData, index);
    }

    @Override
    public ColumnMetadata getColumnMetadata(String name) {
        return new R2DBCOnJdbcColumnMetadata(resultSetMetaData, columnNamesIndices.get(name));
    }

    @Override
    public Iterable<? extends ColumnMetadata> getColumnMetadatas() {
        throw new NotImplementedException();
    }

    @Override
    public Collection<String> getColumnNames() {
        return columnNamesIndices.keySet();
    }

    public int getColumnIndex(String name) {
        return columnNamesIndices.get(name);
    }
}
