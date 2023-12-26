package com.dominik.courses.model.dto;

import jakarta.validation.constraints.NotBlank;

public record StudentDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String email
) {
}
