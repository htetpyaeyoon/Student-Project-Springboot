package com.student.StudentRegisterwithSpringBoot.repository;

import com.student.StudentRegisterwithSpringBoot.model.CourseBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepo extends JpaRepository<CourseBean,String> {
    @Query("select MAX(c.id) from CourseBean c")
    String findLastId();

    @Query("SELECT c FROM CourseBean c WHERE c.course_name = :course_name")
    CourseBean findByCourseName(@Param("course_name") String courseName);

}