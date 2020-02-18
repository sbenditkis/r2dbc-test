package com.sapiens.tests.r2dbc.repo;

import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
@RequiredArgsConstructor
public class PGObjectToStringConverter implements Converter<PGobject, String> {
    @Override
    public String convert(PGobject pGobject) {
        return pGobject.getValue();
    }
}