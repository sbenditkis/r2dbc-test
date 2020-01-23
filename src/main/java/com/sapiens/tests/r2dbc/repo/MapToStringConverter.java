package com.sapiens.tests.r2dbc.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.Map;

@WritingConverter
@RequiredArgsConstructor
public class MapToStringConverter implements Converter<Map<String, Object>, String> {
    private final ObjectMapper objectMapper;

    @Override
    public String convert(Map<String, Object> source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}