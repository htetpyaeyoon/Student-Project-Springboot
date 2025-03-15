package com.student.StudentRegisterwithSpringBoot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class CourseBean {
    @Id
    @Column(length = 45, nullable = false,name = "id")
    private String id;

    @Column(length = 45,nullable = false, name = "course_name")
    private String course_name;

    @Column(name = "deleted") // Map to a column named 'deleted' in the database
    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}

