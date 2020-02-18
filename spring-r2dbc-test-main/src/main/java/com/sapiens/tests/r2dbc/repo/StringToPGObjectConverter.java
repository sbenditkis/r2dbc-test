package com.sapiens.tests.r2dbc.repo;

import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.sql.SQLException;

@WritingConverter
@RequiredArgsConstructor
public class StringToPGObjectConverter implements Converter<String, PGobject> {
    @Override
    public PGobject convert(String source) {
        PGobject pGobject = new PGobject();
        try {
            pGobject.setValue(source);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pGobject;
    }
}