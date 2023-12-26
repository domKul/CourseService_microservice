package com.dominik.courses.model.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CourseMembers {
    private LocalDateTime localDateTime;
    @NotNull
    private String email;

    public CourseMembers( String email) {
        this.localDateTime = LocalDateTime.now();
        this.email = email;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getEmail() {
        return email;
    }
}
