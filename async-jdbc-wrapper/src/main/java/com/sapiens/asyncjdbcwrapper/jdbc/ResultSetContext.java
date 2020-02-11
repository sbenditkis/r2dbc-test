package com.sapiens.asyncjdbcwrapper.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;

@Data
@RequiredArgsConstructor
public class ResultSetContext {
    private final Connection connection;
    private final ResultSet rs;
    private final int chunkSize;
    private int totalRead = 0;
}
