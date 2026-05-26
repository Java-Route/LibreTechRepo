package com.librotech.catalog.dto;

import jakarta.validation.constraints.NotBlank;

public record BookDescriptionRequest(
        @NotBlank(message = "Description field is required")
        String description
) {
}
