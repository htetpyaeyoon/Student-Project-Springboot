package com.student.StudentRegisterwithSpringBoot.Service;

import com.student.StudentRegisterwithSpringBoot.model.StudentBean;
import com.student.StudentRegisterwithSpringBoot.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    @Autowired
    StudentRepo sturepo;

    public String generateId() {
        String lastId = sturepo.findLastId();
        if (lastId == null) {
            return "STU001";
        } else {
            int id = Integer.parseInt(lastId.substring(3)) + 1;
            String nextId = "STU" + String.format("%03d", id);
            return nextId;
        }
    }

    public void insertStuent(StudentBean obj) {
        String id = generateId();
        obj.setId(id);
        sturepo.save(obj);
    }


    public List<StudentBean> selectAllStudent() {
        List<Object[]> studentData = sturepo.findAllStudentsWithCourseName();
        Map<String, StudentBean> studentMap = new HashMap<>();

        for (Object[] data : studentData) {
            String studentId = ((StudentBean) data[0]).getId();
            String courseName = (String) data[1];

            StudentBean student = studentMap.getOrDefault(studentId, (StudentBean) data[0]);
            student.setId(studentId);
            student.setCourse_name(student.getCourse_name() != null ? student.getCourse_name() + ", " + courseName : courseName);

            studentMap.put(studentId, student);
        }

        return new ArrayList<>(studentMap.values());
    }

    public Optional<StudentBean> getStudentByid(String id) {
        return sturepo.findById(id);
    }

    public StudentBean selectOne(String id) {
        return sturepo.findById(id).get();
    }

    public void update(StudentBean obj) {
        sturepo.save(obj);
    }

    /*@Transactional*/
    public void softDeleteStudent(String id) {
        Optional<StudentBean> studentOptional = sturepo.findById(id);
        studentOptional.ifPresent(student -> {
            student.setDeleted(true); // Set deleted flag to true
            sturepo.save(student);
        });
    }

}

