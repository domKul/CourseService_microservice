package com.dominik.courses.controller;

import com.dominik.courses.model.Course;
import com.dominik.courses.model.dto.StudentDto;
import com.dominik.courses.service.CourseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseServiceImpl courseService;


    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;

    }
    @GetMapping
    public List<Course>findCourses(@RequestParam(required = false) String name){
        return courseService.getCourses(name);
    }
    @GetMapping("/{code}")
    public Course findCourseByCode(@PathVariable String code){
        return courseService.getCourse(code);
    }

    @PostMapping
    public Course createCourse(@RequestBody @Valid Course course){
        return courseService.addCourse(course);
    }

    @PutMapping("/{code}")
    public Course updateCourse(@PathVariable String code,@RequestBody @Valid Course course){
        return courseService.modifyCourse(code, course);
    }

    @PatchMapping("/{code}")
    public Course patchCourse(@PathVariable String code,@RequestBody @Valid Course course){
        return courseService.patchCourse(code, course);
    }
   @PostMapping("/student/{studentId}/{courseCode}")
    public ResponseEntity<?>courseEnrollment(@PathVariable Long studentId, @PathVariable String courseCode){
        courseService.courseEnrollment(studentId,courseCode);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/{code}/members")
    public List<StudentDto> getStudentsFromCourseByCode(@PathVariable String code){
        return courseService.getStudentsEnrolledInCourse(code);
    }

    @PostMapping("/{courseCode}/finish-enroll")
    public ResponseEntity<?>finishEnroll(@PathVariable String  courseCode){
        courseService.courseFinishEnroll(courseCode);
        return ResponseEntity.ok().build();
    }



}
