package com.student.StudentRegisterwithSpringBoot.repository;

import com.student.StudentRegisterwithSpringBoot.model.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserBean,String> {
    @Query("SELECT MAX(u.id) FROM UserBean u")
   String findLastId();

    @Query("SELECT u FROM UserBean u WHERE u.deleted = false")
    List<UserBean> findNonDeletedUsers();
}
