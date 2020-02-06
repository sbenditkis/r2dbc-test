package com.sapiens.tests.r2dbc.repo;

import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
@RequiredArgsConstructor
public class StringToJsonConverter implements Converter<String, Json> {
    @Override
    public Json convert(String source) {
        return Json.of(source);
    }
}