package com.librotech.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;

    private String title;

    private String author;

    private String isbn;

    private int publicationYear;
}
