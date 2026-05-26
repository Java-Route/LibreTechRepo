package com.librotech.catalog.dto;

import java.time.LocalDate;

public record BookResumeDTO(
        Long id,
        String title,
        LocalDate publicationYear,
        Double price,
        String editorialName,
        String country
) {
}
