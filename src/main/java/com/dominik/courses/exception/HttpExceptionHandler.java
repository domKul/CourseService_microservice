package com.dominik.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ErrorMessage> handleException(CourseException e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (CourseErrorMessages.COURSE_NOT_FOUND.equals(e.getCourseErrorMessages())) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (CourseErrorMessages.COURSE_START_DATE_IS_AFTER_END_DATE.equals(e.getCourseErrorMessages())
                ) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (CourseErrorMessages.COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED.equals(e.getCourseErrorMessages())
                || CourseErrorMessages.COURSE_CAN_NOT_SET_FULL_STATUS.equals(e.getCourseErrorMessages())
                || CourseErrorMessages.COURSE_CAN_NOT_SET_ACTIVE_STATUS.equals(e.getCourseErrorMessages())
                || CourseErrorMessages.STUDENT_ALREADY_ENROLLED.equals(e.getCourseErrorMessages())
                || CourseErrorMessages.COURSE_IS_NOT_ACTIVE.equals(e.getCourseErrorMessages())
                || CourseErrorMessages.COURSE_WITH_GIVEN_CODE_IS_ALREADY_CREATED.equals(e.getCourseErrorMessages())) {
            httpStatus = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorMessage(e.getCourseErrorMessages().getMessage()));
    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorMessage> handleCourseException(CourseException  c) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new ErrorMessage(c.getCourseErrorMessages().getMessage()));
//    }
}
