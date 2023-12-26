package com.dominik.courses.service;

import com.dominik.courses.model.dto.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentServiceClient {


    @GetMapping("students/{studentId}")
    StudentDto getStudentById(@PathVariable Long studentId);

    @PostMapping("students/email")
    List<StudentDto> getStudentByEmail(@RequestBody List<String> emails);

}
