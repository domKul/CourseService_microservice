package com.dominik.courses.service;

import com.dominik.courses.exception.CourseErrorMessages;
import com.dominik.courses.exception.CourseException;
import com.dominik.courses.model.Course;
import com.dominik.courses.model.dto.CourseMembers;
import com.dominik.courses.model.dto.NotificationInfoDto;
import com.dominik.courses.model.dto.StudentDto;
import com.dominik.courses.repository.CourseRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    public static final String EXCHANGE_ENROLL_FINISH = "enroll_finish";
    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;
    private final RabbitTemplate rabbitTemplate;

    public CourseServiceImpl(CourseRepository courseRepository, StudentServiceClient studentServiceClient, RabbitTemplate rabbitTemplate) {
        this.courseRepository = courseRepository;
        this.studentServiceClient = studentServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<Course> getCourses(String name) {
        if (name != null) {
            return courseRepository.findByName(name);
        }
        return courseRepository.findAll();
    }

    @Override
    public Course getCourse(String code) {
        return findCourseInDb(code);
    }

    @Override
    @Transactional
    public Course addCourse(Course course) {
        Optional<Course> byId = courseCheck(course);
        if (byId.isPresent()){
            throw new CourseException(CourseErrorMessages.COURSE_WITH_GIVEN_CODE_IS_ALREADY_CREATED);
        }
        return courseRepository.save(course);
    }

    private Optional<Course> courseCheck(Course course) {
        return courseRepository.findById(course.getCode());
    }

    @Override
    @Transactional
    public void deleteCourse(String code) {
        Course courseInDb = findCourseInDb(code);
        courseRepository.delete(courseInDb);

    }

    @Override
    @Transactional
    public Course modifyCourse(String code, Course course) {
        return courseCheck(course)
                .map(courseInDb -> {
                    courseInDb.setName(course.getName());
                    courseInDb.setDescription(course.getDescription());
                    courseInDb.setEndData(course.getEndData());
                    courseInDb.setStartData(course.getStartData());
                    courseInDb.setParticipantsNumber(course.getParticipantsNumber());
                    courseInDb.setParticipantsLimit(course.getParticipantsLimit());
                    return courseInDb;
                }).orElseThrow(() -> new CourseException(CourseErrorMessages.COURSE_NOT_FOUND));
    }

    @Override
    @Transactional
    public void courseEnrollment(Long studentId, String courseCode) {
        Course courseInDb = findCourseInDb(courseCode);
        validateCourseStatus(courseInDb);
        StudentDto studentById = studentServiceClient.getStudentById(studentId);
        validateStudentBeforeEnrollment(courseInDb, studentById);
        courseInDb.incrementParticipantNumber();
        courseInDb.getCourseMembersList().add(new CourseMembers(studentById.email()));
        courseRepository.save(courseInDb);

    }

    private static void validateStudentBeforeEnrollment(Course courseInDb, StudentDto studentById) {
        if(courseInDb.getCourseMembersList().stream()
                        .anyMatch(student -> student.getEmail().equals(studentById.email()))){
            throw new CourseException(CourseErrorMessages.STUDENT_ALREADY_ENROLLED);
        }
    }

    private static void validateCourseStatus(Course courseInDb) {
        if(!Course.Status.ACTIVE.equals(courseInDb.getStatus())){
            throw new CourseException(CourseErrorMessages.COURSE_NOT_ACTIVE);
        }
    }

    public List<StudentDto>getStudentsEnrolledInCourse(String code){
        Course courseInDb = findCourseInDb(code);
        List<String> list = getEmailListFromCourses(courseInDb);
        return studentServiceClient.getStudentByEmail(list);
    }

    @Transactional
    public Course patchCourse(String code, Course course) {
        return courseRepository.findById(code)
                .map(c -> {
                    if (!StringUtils.isEmpty(course.getName())) {
                        c.setName(course.getName());
                    }
                    if (!StringUtils.isEmpty(course.getDescription())) {
                        c.setDescription(course.getDescription());
                    }
                    if (course.getParticipantsLimit() != null) {
                        c.setParticipantsLimit(course.getParticipantsLimit());
                    }
                    if (course.getParticipantsNumber() != null) {
                        c.setParticipantsLimit(course.getParticipantsLimit());
                    }
                    if (course.getStartData() != null) {
                        c.setStartData(course.getStartData());
                    }
                    if (course.getEndData() != null) {
                        c.setEndData(course.getEndData());
                    }
                    return c;
                })
                .map(courseRepository::save)
                .orElseThrow(() -> new CourseException(CourseErrorMessages.COURSE_NOT_FOUND));
    }

    @Transactional
    public void courseFinishEnroll(String coursCode){
        Course courseInDb = findCourseInDb(coursCode);
        if (Course.Status.ACTIVE.equals(courseInDb.getStatus())){
            courseInDb.setStatus(Course.Status.INACTIVE);
            courseRepository.save(courseInDb);
        }else {
            throw new CourseException(CourseErrorMessages.COURSE_IS_ALREADY_INACTIVE);
        }
        courseNotificationCreator(courseInDb);
    }
    private void courseNotificationCreator(Course courseInDb) {
        NotificationInfoDto notificationInfoDto = createMessageInfo(courseInDb);
        rabbitTemplate.convertAndSend(EXCHANGE_ENROLL_FINISH, notificationInfoDto);
    }

    private  NotificationInfoDto createMessageInfo(Course courseInDb) {
        List<String> list = getEmailListFromCourses(courseInDb);
        return NotificationInfoDto.builder()
                        .courseCode(courseInDb.getCode())
                        .courseName(courseInDb.getName())
                        .courseDescription(courseInDb.getDescription())
                        .courseStartDate(courseInDb.getStartData())
                        .courseEndDate(courseInDb.getEndData())
                        .emails(list)
                        .build();
    }

    private static List<String> getEmailListFromCourses(Course courseInDb) {
        return courseInDb.getCourseMembersList().stream()
                .map(CourseMembers::getEmail)
                .toList();
    }

    private Course findCourseInDb(String code) {
        return courseRepository.findById(code)
                .orElseThrow(() -> new CourseException(CourseErrorMessages.COURSE_NOT_FOUND));
    }
}
