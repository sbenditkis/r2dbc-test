package com.sapiens.asyncjdbcwrapper.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;

@Data
@AllArgsConstructor
public class ResultSetContext {
    private Connection connection;
    private ResultSet rs;
}
