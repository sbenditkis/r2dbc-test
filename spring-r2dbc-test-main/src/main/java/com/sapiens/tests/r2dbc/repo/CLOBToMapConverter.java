package com.sapiens.tests.r2dbc.repo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import oracle.sql.CLOB;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@ReadingConverter
@RequiredArgsConstructor
public class CLOBToMapConverter implements Converter<CLOB, Map<String, Object>> {
    private final ObjectMapper objectMapper;

    @Override
    public Map<String, Object> convert(CLOB json) {
        try {
            return objectMapper.readValue(json.stringValue(), new TypeReference<Map>() {
            });
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}