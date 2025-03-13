package com.student.StudentRegisterwithSpringBoot.model;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Entity
@Table(name = "student")

public class StudentBean {
    @Id
    @Column(length = 45, nullable = false, name = "id")
    private String id;
    @Column(length = 45, nullable = false, name = "name")
    private String name;
    @Column(length = 45, nullable = false, name = "dob")
    private String dob;
    @Column(length = 45, nullable = false, name = "gender")
    private String gender;
    @Column(length = 45, nullable = false, name = "phone")
    private String phone;
    @Column(length = 45, nullable = false, name = "education")
    private String education;
    @Column(length = 45, nullable = false, name = "attend")
    @Transient
    private String[] attend;
    @Column(length = 200, nullable = false, name = "photo")
    private String photo;
    @Transient
    private String course_name;
    @Transient
    private MultipartFile file;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String[] getAttend() {
        return attend;
    }

    public void setAttend(String[] attend) {
        this.attend = attend;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}

