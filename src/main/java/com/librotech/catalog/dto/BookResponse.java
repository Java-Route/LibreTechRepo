package com.librotech.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;

    private String title;

    private String author;

    private String isbn;

    private LocalDate publicationDate;

    private String editorialName;

    private String genreName;
}
