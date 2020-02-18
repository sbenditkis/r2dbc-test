package com.sapiens.bdms;

import io.r2dbc.spi.Result;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiFunction;

public class R2DBCOnJdbcResult implements Result {

    private ResultSet resultSet;
    private R2DBCOnJdbcRowMetadata rowMetadata;

    public R2DBCOnJdbcResult(ResultSet resultSet) {
        this.resultSet = resultSet;
        try {
            this.rowMetadata = new R2DBCOnJdbcRowMetadata(resultSet.getMetaData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Publisher<Integer> getRowsUpdated() {
        return Mono.just(0);
    }

    @Override
    public <T> Publisher<T> map(BiFunction<Row, RowMetadata, ? extends T> mappingFunction) {
        return Flux.generate(() -> resultSet, (rs, sink)-> {
            try {
                if(rs.next()) {
                    sink.next(new R2DBCOnJdbcRow(rs, rowMetadata));
                } else {
                    sink.complete();
                }
            } catch (SQLException e) {
                sink.error(e);
            }
            return rs;
        }).map(row-> {
            return mappingFunction.apply((Row)row, rowMetadata);
        });
    }
}
