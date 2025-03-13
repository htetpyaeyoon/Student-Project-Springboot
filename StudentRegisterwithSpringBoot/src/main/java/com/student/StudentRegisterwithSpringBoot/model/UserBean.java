package com.student.StudentRegisterwithSpringBoot.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name = "user")
public class UserBean {

    @Id
    @Column(length = 45, nullable = false, name = "id")
    private String id;
    @Column(length=45,nullable = false,name = "name")
    private String name;
    @Column(length = 45,nullable = false,name = "email")
    private String email;
    @Column(length = 45,nullable = false,name = "password")
    private String password;
    @Transient
    private String confirmPassword;
    @Column(length = 45,nullable = false,name = "role")
    private String role;

    @Column(name = "deleted")
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

