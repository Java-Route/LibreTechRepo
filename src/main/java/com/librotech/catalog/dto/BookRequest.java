package com.librotech.catalog.dto;


import com.librotech.catalog.validation.OnCreate;
import com.librotech.catalog.validation.OnPatch;
import com.librotech.catalog.validation.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.Message;

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

    private Integer publicationYear;
}
