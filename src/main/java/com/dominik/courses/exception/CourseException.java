package com.dominik.courses.exception;

public class CourseException extends RuntimeException{
    private final CourseErrorMessages courseErrorMessages;

    public CourseException(CourseErrorMessages courseErrorMessages) {
        this.courseErrorMessages = courseErrorMessages;
    }

    public CourseErrorMessages getCourseErrorMessages() {
        return courseErrorMessages;
    }
}
