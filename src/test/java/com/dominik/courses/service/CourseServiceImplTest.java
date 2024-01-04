package com.dominik.courses.service;

import com.dominik.courses.exception.CourseErrorMessages;
import com.dominik.courses.exception.CourseException;
import com.dominik.courses.model.Course;
import com.dominik.courses.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        course2 = new Course();
        course2.setCode("COURSE2");
        course2.setName("Java Programming");
        course2.setDescription("Learn the basics of Java programmin2");
        course2.setStartData(LocalDateTime.of(2024, 11, 2, 10, 0));
        course2.setEndData(LocalDateTime.of(2024, 11, 22, 18, 0));
        course2.setParticipantsLimit(10L);
        course2.setParticipantsNumber(5L);
        course2.setStatus(Course.Status.ACTIVE);
        course3 = new Course();
        course3.setCode("COURSE3");
        course3.setName("Java Programming v2");
        course3.setDescription("Learn the basics of Java programmin2");
        course3.setStartData(LocalDateTime.of(2024, 11, 2, 10, 0));
        course3.setEndData(LocalDateTime.of(2024, 11, 22, 18, 0));
        course3.setParticipantsLimit(10L);
        course3.setParticipantsNumber(5L);
        course3.setStatus(Course.Status.ACTIVE);

    }

    @Test
    void shouldCreateAndAddCourse() {
        //Given
        when(courseService.addCourse(course1)).thenReturn(course1);

        //When
        Course addedCourse = courseService.addCourse(course1);

        //Then
        assertNotNull(addedCourse);
        assertEquals(course1.getCode(), addedCourse.getCode());
        assertEquals(course1.getName(), addedCourse.getName());
        assertEquals(course1.getCourseMembersList(), addedCourse.getCourseMembersList());
        assertEquals(course1.getParticipantsNumber(), addedCourse.getParticipantsNumber());
        assertEquals(course1.getStatus(), addedCourse.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenAddingCourseWithCodeTakenByAnotherCourse() {
        //Given
        when(courseRepository.findById(course1.getCode())).thenReturn(Optional.ofNullable(course1));


        //When
        CourseException courseException = assertThrows(CourseException.class,
                () -> courseService.addCourse(course1));

        //Then
        assertEquals(CourseErrorMessages.COURSE_WITH_GIVEN_CODE_IS_ALREADY_CREATED.getMessage(),
                courseException.getCourseErrorMessages().getMessage());
    }

    @Test
    void shouldFindCourseByPassingCode() {
        //Given
        when(courseRepository.findById(course1.getCode())).thenReturn(Optional.ofNullable(course1));

        //When
        Course courseById = courseService.getCourse(course1.getCode());

        //Then
        assertNotNull(courseById);
        assertEquals(course1.getName(), courseById.getName());
    }


    @Test
    void shouldHandleWrongCodeExceptionWhenTryingToFindByCode() {
        //Given
        String wrongId = "123123123L";
        when(courseRepository.findById(wrongId)).thenReturn(Optional.empty());

        //When
        CourseException courseException = assertThrows(CourseException.class,
                () -> courseService.getCourse(wrongId));

        //Then
        assertEquals(CourseErrorMessages.COURSE_NOT_FOUND.getMessage(),
                courseException.getCourseErrorMessages().getMessage());

    }

    @Test
    void shouldFindCourseByName(){
        //Given
        String name = course1.getName();
        List<Course> courses = List.of(course1,course2);
        when(courseService.getCourses(name)).thenReturn(courses);

        //When
        List<Course> gettingCourseList = courseService.getCourses(name);

        //Then
        assertEquals(2,gettingCourseList.size());
        assertEquals(course1.getCode(),gettingCourseList.get(0).getCode());
        assertEquals(course2.getCode(),gettingCourseList.get(1).getCode());
    }

    @Test
    void shouldFindListOfAllCoursesFromDB(){
        //Given
        List<Course> courses = List.of(course1, course2, course3);
        when(courseService.getCourses(null)).thenReturn(courses);

        //When
        List<Course> listWithoutName = courseService.getCourses(null);

        //Then
        assertNotNull(listWithoutName);
        assertEquals(3,listWithoutName.size());
    }


}
