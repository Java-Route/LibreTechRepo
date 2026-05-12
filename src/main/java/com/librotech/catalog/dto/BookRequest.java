package com.librotech.catalog.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Title field is required")
    private String title;

    @NotBlank(message = "Author field is required")
    private String author;

    @NotBlank(message = "isbn field is required")
    private String isbn;

    private int publicationYear;
}
