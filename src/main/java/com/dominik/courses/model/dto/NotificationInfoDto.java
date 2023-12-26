package com.dominik.courses.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
public class NotificationInfoDto {

    private List<String> emails;
    private String courseCode;
    private String courseName;
    private String courseDescription;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime courseStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime courseEndDate;

    private NotificationInfoDto() {
    }

    public List<String> getEmails() {
        return emails;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public LocalDateTime getCourseStartDate() {
        return courseStartDate;
    }

    public LocalDateTime getCourseEndDate() {
        return courseEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationInfoDto that = (NotificationInfoDto) o;
        return Objects.equals(emails, that.emails) && Objects.equals(courseCode, that.courseCode) && Objects.equals(courseName, that.courseName) && Objects.equals(courseDescription, that.courseDescription) && Objects.equals(courseStartDate, that.courseStartDate) && Objects.equals(courseEndDate, that.courseEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emails, courseCode, courseName, courseDescription, courseStartDate, courseEndDate);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<String> emails;
        private String courseCode;
        private String courseName;
        private String courseDescription;
        private LocalDateTime courseStartDate;
        private LocalDateTime courseEndDate;

        // Prywatny konstruktor buildera
        private Builder() {
        }

        // Metody do ustawiania wartości pól buildera i zwracania samego siebie

        public Builder emails(List<String> emails) {
            this.emails = emails;
            return this;
        }

        public Builder courseCode(String courseCode) {
            this.courseCode = courseCode;
            return this;
        }

        public Builder courseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public Builder courseDescription(String courseDescription) {
            this.courseDescription = courseDescription;
            return this;
        }

        public Builder courseStartDate(LocalDateTime courseStartDate) {
            this.courseStartDate = courseStartDate;
            return this;
        }

        public Builder courseEndDate(LocalDateTime courseEndDate) {
            this.courseEndDate = courseEndDate;
            return this;
        }

        // Metoda do budowania instancji NotificationInfoDto
        public NotificationInfoDto build() {
            NotificationInfoDto notificationInfoDto = new NotificationInfoDto();
            notificationInfoDto.emails = this.emails;
            notificationInfoDto.courseCode = this.courseCode;
            notificationInfoDto.courseName = this.courseName;
            notificationInfoDto.courseDescription = this.courseDescription;
            notificationInfoDto.courseStartDate = this.courseStartDate;
            notificationInfoDto.courseEndDate = this.courseEndDate;
            return notificationInfoDto;
        }
    }


}

