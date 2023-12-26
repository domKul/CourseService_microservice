package com.dominik.courses.service;

import com.dominik.courses.model.Course;
import com.dominik.courses.model.dto.StudentDto;

import java.util.List;

public interface CourseService {
    List<Course>getCourses(String name);
    Course getCourse(String code);

    Course addCourse(Course course);
    void deleteCourse(String code);
    Course modifyCourse(String code ,Course course);
    void courseEnrollment(Long studentId, String courseCode);
    List<StudentDto>getStudentsEnrolledInCourse(String code);
}
