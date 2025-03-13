package com.student.StudentRegisterwithSpringBoot.repository;

import com.student.StudentRegisterwithSpringBoot.model.StudentBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepo extends JpaRepository<StudentBean,String> {
    @Query("select MAX (c.id) from StudentBean c")
    String findLastId();

    @Query("SELECT s, sa.course.course_name " +
            "FROM StudentBean s " +
            "INNER JOIN StudentAttendBean sa ON s.id = sa.student.id " +
            "WHERE s.deleted = false OR s.deleted IS NULL " +
            "GROUP BY s, sa.course.course_name")
    List<Object[]> findAllStudentsWithCourseName();

/*    @Query("SELECT s, c " +
            "FROM StudentBean s " +
            "JOIN StudentAttendBean sa ON s.id = sa.student.id " +
            "JOIN CourseBean c ON sa.course.id = c.id " +
            "WHERE s.deleted = false")
    List<Object[]> findAllStudentsWithCourseName();*/


    /*@Query("SELECT s, sa.course.course_name FROM StudentBean s JOIN StudentAttendBean sa ON s.id = sa.student.id WHERE s.id = ?1")
    List<Object[]> findStudentWithCourseById(String studentId);*/
}


