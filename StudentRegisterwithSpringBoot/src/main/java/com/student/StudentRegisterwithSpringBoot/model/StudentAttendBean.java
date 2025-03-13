package com.student.StudentRegisterwithSpringBoot.model;

import com.student.StudentRegisterwithSpringBoot.model.CourseBean;
import com.student.StudentRegisterwithSpringBoot.model.StudentBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Transient;

@Entity
public class StudentAttendBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 45,name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentBean student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseBean course;

    @Transient
    private String studentId;

    @Transient
    private String courseId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }



}
