package com.sapiens.tests.r2dbc.repo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;
import java.util.Map;

@ReadingConverter
@RequiredArgsConstructor
public class StringToMapConverter implements Converter<String, Map<String, Object>> {
    private final ObjectMapper objectMapper;

    @Override
    public Map<String, Object> convert(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}