package com.student.StudentRegisterwithSpringBoot.Service;

import com.student.StudentRegisterwithSpringBoot.model.StudentAttendBean;
import com.student.StudentRegisterwithSpringBoot.repository.AttendRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendService {

    @Autowired
    AttendRepo attendRepo;

    public void insert(StudentAttendBean bean) {
        attendRepo.save(bean);
    }

    public List<StudentAttendBean> findByStudentId(String id) {
        List<StudentAttendBean> list = attendRepo.findByStudentId(id);
        return list;
    }

    public void deleteStudentByid(String id) {
        attendRepo.deleteByStudentId(id);
    }

}
