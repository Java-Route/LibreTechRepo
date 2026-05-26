package com.librotech.catalog.dto;


import com.librotech.catalog.validation.OnCreate;
import com.librotech.catalog.validation.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record BookRequest(
        @NotBlank(message = "Title field is required", groups = {OnCreate.class, OnUpdate.class})
        String title,

        @NotBlank(message = "Author field is required", groups = {OnCreate.class, OnUpdate.class})
        String author,

        @NotBlank(message = "isbn field is required", groups = {OnCreate.class, OnUpdate.class})
        String isbn,

        @NotNull(message = "Publication date field is required", groups = {OnCreate.class, OnUpdate.class})
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate publicationDate,

        @NotNull(message = "Category field is required", groups = {OnCreate.class, OnUpdate.class})
        Long genreId,

        @NotNull(message = "Editorial field is required", groups = {OnCreate.class, OnUpdate.class})
        Long editorialId
) {
}
