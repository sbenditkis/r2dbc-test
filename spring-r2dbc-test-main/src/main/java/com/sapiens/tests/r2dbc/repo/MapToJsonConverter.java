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
public class MapToJsonConverter implements Converter<Map<String, Object>, Json> {
    private final ObjectMapper objectMapper;

    @Override
    public Json convert(Map<String, Object> source) {
        try {
            return Json.of(objectMapper.writeValueAsString(source));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}