package com.dominik.courses.service;

import com.dominik.courses.model.Course;
import com.dominik.courses.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CourseServiceImplTest {
    @InjectMocks
    private CourseServiceImpl courseService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private StudentServiceClient studentServiceClient;

    private Course course1;
    private Course course2;
    private Course course3;


    @BeforeEach
    void setUp() {
        course1 = new Course();
        course1.setCode("COURSE1");
        course1.setName("Java Programming");
        course1.setDescription("Learn the basics of Java programming");
        course1.setStartData(LocalDateTime.of(2024, 10, 4, 10, 0));
        course1.setEndData(LocalDateTime.of(2024, 10, 29, 18, 0));
        course1.setParticipantsLimit(10L);
        course1.setParticipantsNumber(5L);
        course1.setStatus(Course.Status.ACTIVE);

    }

    @Test
    void shouldCreateAndAddCourse() {
        //Given
        when(courseService.addCourse(course1)).thenReturn(course1);

        //When
        Course addedCourse = courseService.addCourse(course1);

        //Then
        assertNotNull(addedCourse);
        assertEquals(course1.getCode(),addedCourse.getCode());
        assertEquals(course1.getName(),addedCourse.getName());
        assertEquals(course1.getCourseMembersList(),addedCourse.getCourseMembersList());
        assertEquals(course1.getParticipantsNumber(),addedCourse.getParticipantsNumber());
        assertEquals(course1.getStatus(),addedCourse.getStatus());
    }
}
