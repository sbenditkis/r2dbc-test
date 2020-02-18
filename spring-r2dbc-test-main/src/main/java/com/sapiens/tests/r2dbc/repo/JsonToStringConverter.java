package com.sapiens.tests.r2dbc.repo;

import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
@RequiredArgsConstructor
public class JsonToStringConverter implements Converter<Json, String> {
    @Override
    public String convert(Json json) {
        return json.asString();
    }
}