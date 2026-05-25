package com.librotech.catalog.dto;


import com.librotech.catalog.validation.OnCreate;
import com.librotech.catalog.validation.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Title field is required", groups = {OnCreate.class , OnUpdate.class})
    private String title;

    @NotBlank(message = "Author field is required", groups = {OnCreate.class , OnUpdate.class})
    private String author;

    @NotBlank(message = "isbn field is required", groups = {OnCreate.class , OnUpdate.class})
    private String isbn;

    @NotNull(message = "Publication date field is required", groups = {OnCreate.class, OnUpdate.class})
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate publicationDate;
}
