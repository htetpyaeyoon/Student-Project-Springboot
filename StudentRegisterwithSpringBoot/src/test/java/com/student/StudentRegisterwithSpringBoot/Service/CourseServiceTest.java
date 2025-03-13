package com.student.StudentRegisterwithSpringBoot.Service;

import com.student.StudentRegisterwithSpringBoot.model.CourseBean;
import com.student.StudentRegisterwithSpringBoot.repository.CourseRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    CourseRepo courseRepo;

    @InjectMocks
    CourseService courseService;

    @Test
    public void insertCourse() {
        CourseBean course=new CourseBean();
        course.setCourse_name("OJT");
        when(courseRepo.findLastId()).thenReturn("C001");
        courseService.insertCourse(course);
        verify(courseRepo,times(1)).save(course);
        assertEquals("C002", course.getId());
    }

    @Test
    public void generateId() {
        when(courseRepo.findLastId()).thenReturn(null);
        String generatedId = courseService.generateId();
        assertEquals("C001", generatedId);
    }


    @Test
    public void selectAll() {
        List<CourseBean> list= new ArrayList<>();
        when(courseRepo.findAll()).thenReturn(list);
        List<CourseBean> result= courseService.selectAll();
        assertEquals(list,result);
    }

    @Test
    public void selectOne() {
        String courseId = "C001";
        CourseBean course = new CourseBean();
        when(courseRepo.findById(courseId)).thenReturn(Optional.of(course));
        CourseBean result = courseService.selectOne(courseId);
        assertEquals(course, result);

    }

    @Test
    public void getCourseByName() {
        String courseName = "Java Programming";
        CourseBean mockedCourse = new CourseBean();
        when(courseRepo.findByCourseName(courseName)).thenReturn(mockedCourse);
        CourseBean result = courseService.getCourseByName(courseName);
        assertEquals(mockedCourse, result);
    }
}