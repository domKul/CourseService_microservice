package com.dominik.courses.exception;

import com.dominik.courses.model.Course;

public enum CourseErrorMessages {

    COURSE_NOT_FOUND("Course not found"),
    COURSE_NOT_ACTIVE("Course are not active"),
    STUDENT_ALREADY_ENROLLED("Student already enrolled on this course"),
    COURSE_START_DATE_IS_AFTER_END_DATE("Course start date is after end date"),
    COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED("Course participants limit is exceeded"),
    COURSE_CAN_NOT_SET_FULL_STATUS("Course can not set Full status"),
    COURSE_CAN_NOT_SET_ACTIVE_STATUS("Course can not set Active status." +
            " Participants limit is equals participants number"),
    COURSE_IS_NOT_ACTIVE("Course is not Active"),
    COURSE_IS_ALREADY_INACTIVE("Course is already Inactive");
    private final String message;

    CourseErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
