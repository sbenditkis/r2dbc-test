package com.sapiens.bdms;

import io.r2dbc.spi.*;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import java.sql.SQLException;

public class R2DBCOnJdbcConnection implements Connection {

    private final java.sql.Connection jdbcConnection;
    private PooledConnection pooledConnection;
    private boolean autoCommit;
    private IsolationLevel isolationLevel;

    @Autowired
    public R2DBCOnJdbcConnection(DataSource dataSource) {
        try {
            if (dataSource instanceof ConnectionPoolDataSource) {
                this.pooledConnection = ((ConnectionPoolDataSource) dataSource).getPooledConnection();
                this.jdbcConnection = pooledConnection.getConnection();
            } else {
                this.jdbcConnection = dataSource.getConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public java.sql.Connection getJdbcConnection() throws SQLException {
        return jdbcConnection;
    }

    @Override
    public Publisher<Void> beginTransaction() {
        return null;
    }

    @Override
    public Publisher<Void> close() {
        return Mono.defer(()-> {
            try {
                if(pooledConnection!=null) {
                    pooledConnection.close();
                } else {
                    jdbcConnection.close();
                }
                return Mono.empty();
            } catch (SQLException e) {
                return Mono.error(e);
            }
        });
    }

    @Override
    public Publisher<Void> commitTransaction() {
        throw new NotImplementedException();
    }

    @Override
    public Batch createBatch() {
        throw new NotImplementedException();
    }

    @Override
    public Publisher<Void> createSavepoint(String name) {
        throw new NotImplementedException();
    }

    @Override
    public Statement createStatement(String sql) {
        return new R2DBCOnJdbcStatement(sql, this);
    }

    @Override
    public boolean isAutoCommit() {
        return autoCommit;
    }

    @Override
    public ConnectionMetadata getMetadata() {
        throw new NotImplementedException();
    }

    @Override
    public IsolationLevel getTransactionIsolationLevel() {
        return isolationLevel;
    }

    @Override
    public Publisher<Void> releaseSavepoint(String name) {
        throw new NotImplementedException();
    }

    @Override
    public Publisher<Void> rollbackTransaction() {
        throw new NotImplementedException();
    }

    @Override
    public Publisher<Void> rollbackTransactionToSavepoint(String name) {
        throw new NotImplementedException();
    }

    @Override
    public Publisher<Void> setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
        return Mono.empty();
    }

    @Override
    public Publisher<Void> setTransactionIsolationLevel(IsolationLevel isolationLevel) {
        this.isolationLevel = isolationLevel;
        return Mono.empty();
    }

    @Override
    public Publisher<Boolean> validate(ValidationDepth depth) {
        throw new NotImplementedException();
    }
}
