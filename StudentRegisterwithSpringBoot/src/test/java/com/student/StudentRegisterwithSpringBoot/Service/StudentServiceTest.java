package com.student.StudentRegisterwithSpringBoot.Service;

import com.student.StudentRegisterwithSpringBoot.model.StudentBean;
import com.student.StudentRegisterwithSpringBoot.repository.StudentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    StudentRepo studentRepo;

    @InjectMocks
    StudentService studentService;

    @Test
    public void generateId() {
    when(studentRepo.findLastId()).thenReturn(null);
    String lastId=studentService.generateId();
    assertEquals("STU001",lastId);
    }

    @Test
    public void generateId_whenLastIdIsNotNull() {
        when(studentRepo.findLastId()).thenReturn("STU002");
        String generatedId = studentService.generateId();
        assertEquals("STU003", generatedId);
    }

    @Test
    public void insertStuent() {
        StudentBean bean=new StudentBean();
        studentService.insertStuent(bean);
        verify(studentRepo, times(1)).save(bean);
    }

    @Test
    public void selectAllStudent() {
    List<Object[]> list=new ArrayList<>();
    StudentBean stu1=new StudentBean();
    stu1.setId("STU001");
    StudentBean stu2=new StudentBean();
    stu2.setId("STU002");
    list.add(new Object[]{stu1,"Java"});
    list.add(new Object[]{stu2,"PHP"});

    when(studentRepo.findAllStudentsWithCourseName()).thenReturn(list);

    List<StudentBean> result= studentService.selectAllStudent();
        assertEquals(2, result.size());
        assertEquals("STU001", result.get(0).getId());
        assertEquals("Java", result.get(0).getCourse_name());
        assertEquals("STU002", result.get(1).getId());
        assertEquals("PHP", result.get(1).getCourse_name());

    }

    @Test
    public void getStudentByid() {
    String id="STU001";
    StudentBean stu=new StudentBean();
    when(studentRepo.findById(id)).thenReturn(Optional.of(stu));
    Optional<StudentBean> result=studentService.getStudentByid(id);
        assertTrue(result.isPresent());
        assertEquals(stu, result.get());
    }

    @Test
    public void selectOne() {
    String id="STU001";
    StudentBean bean=new StudentBean();
    when(studentRepo.findById(id)).thenReturn(Optional.of(bean));
    StudentBean result=studentService.selectOne(id);
    assertEquals(bean,result);
    }

    @Test
    public void update() {
    StudentBean stud=new StudentBean();
    studentService.update(stud);
    verify(studentRepo, times(1)).save(stud);
    }

    @Test
    public void softDeleteStudent() {
    String id="STU001";
    StudentBean student=new StudentBean();
    when(studentRepo.findById(id)).thenReturn(Optional.of(student));
    studentService.softDeleteStudent(id);
    assertTrue(student.getDeleted());
    verify(studentRepo,times(1)).save(student);
    }
}