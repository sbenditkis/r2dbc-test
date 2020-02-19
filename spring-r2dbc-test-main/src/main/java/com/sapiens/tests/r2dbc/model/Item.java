package com.sapiens.tests.r2dbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("items")
public class Item {
    @Id
    private Integer id;

    private Map<String, Object> data;

    private LocalDateTime created = LocalDateTime.now();
}
