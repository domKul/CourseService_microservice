package com.dominik.courses.model;

import com.dominik.courses.model.dto.CourseMembers;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Course {
    @Id
    private String code;
    @NotBlank
    @Size(min = 3)
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @Future
    private LocalDateTime startData;
    @NotNull
    @Future
    private LocalDateTime endData;

    private Long participantsLimit;
    @NotNull
    @Min(0)
    private Long participantsNumber;

    private Status status;
    private List<CourseMembers> courseMembersList = new ArrayList<>();

    public enum Status{
        ACTIVE,
        INACTIVE,
        FULL
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(code, course.code) && Objects.equals(name, course.name) && Objects.equals(description, course.description) && Objects.equals(startData, course.startData) && Objects.equals(endData, course.endData) && Objects.equals(participantsLimit, course.participantsLimit) && Objects.equals(participantsNumber, course.participantsNumber) && status == course.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, description, startData, endData, participantsLimit, participantsNumber, status);
    }

    public void incrementParticipantNumber(){
        participantsNumber++;
        if(getParticipantsNumber().equals(getParticipantsLimit())){
            setStatus(Course.Status.FULL);
        }
    }

    public List<CourseMembers> getCourseMembersList() {
        return courseMembersList;
    }

    public void setCourseMembersList(List<CourseMembers> courseMembersList) {
        this.courseMembersList = courseMembersList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartData() {
        return startData;
    }

    public void setStartData(LocalDateTime startData) {
        this.startData = startData;
    }

    public LocalDateTime getEndData() {
        return endData;
    }

    public void setEndData(LocalDateTime endData) {
        this.endData = endData;
    }

    public Long getParticipantsLimit() {
        return participantsLimit;
    }

    public void setParticipantsLimit(Long participantsLimit) {
        this.participantsLimit = participantsLimit;
    }

    public Long getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(Long participantsNumber) {
        this.participantsNumber = participantsNumber;
    }
}
