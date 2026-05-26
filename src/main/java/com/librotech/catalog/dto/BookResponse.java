package com.librotech.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

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

    private Double price;

    private Set<String> genreName;
}
