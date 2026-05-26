package com.librotech.catalog.dto;

import java.time.LocalDate;
import java.util.Set;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        LocalDate publicationDate,
        String editorialName,
        Double price,
        Set<String> genreName
) {
}
