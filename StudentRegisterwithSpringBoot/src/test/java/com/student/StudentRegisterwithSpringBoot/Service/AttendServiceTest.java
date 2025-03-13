package com.student.StudentRegisterwithSpringBoot.Service;

import com.student.StudentRegisterwithSpringBoot.model.StudentAttendBean;
import com.student.StudentRegisterwithSpringBoot.repository.AttendRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AttendServiceTest {

    @Mock
    AttendRepo attendRepo;

    @InjectMocks
    AttendService attendService;

    @Test
    public void insert() {
        StudentAttendBean obj=new StudentAttendBean();
        attendService.insert(obj);
        verify(attendRepo,times(1)).save(obj);
    }

    @Test
    public void findByStudentId() {
    List<StudentAttendBean> list=new ArrayList<>();
    when(attendRepo.findByStudentId("studentId")).thenReturn(list);
    List<StudentAttendBean> result = attendService.findByStudentId("studentId");
    assertEquals(list, result);
    }

    @Test
    public void deleteStudentByid() {
    attendService.deleteStudentByid("studentId");
    verify(attendRepo,times(1)).deleteByStudentId("studentId");
    }
}