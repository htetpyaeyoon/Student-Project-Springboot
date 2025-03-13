package com.student.StudentRegisterwithSpringBoot.repository;

import com.student.StudentRegisterwithSpringBoot.model.StudentAttendBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttendRepo extends JpaRepository<StudentAttendBean,String> {
    @Query("SELECT sc FROM StudentAttendBean sc WHERE sc.student.id = :studentId")
    List<StudentAttendBean> findByStudentId(String studentId);

    @Modifying
    @Query("delete  from StudentAttendBean sc where sc.student.id=:studentId")
    void  deleteByStudentId(String studentId);
}
