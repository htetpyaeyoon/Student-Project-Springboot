package com.student.StudentRegisterwithSpringBoot.Service;

import com.student.StudentRegisterwithSpringBoot.model.UserBean;
import com.student.StudentRegisterwithSpringBoot.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepo userrepo;

    public void adduser(UserBean bean) {
        String id = generateNextUserId();
        bean.setId(id);
        userrepo.save(bean);
    }

    public void update(UserBean user) {
        userrepo.save(user);
    }

    public String generateNextUserId() {
        String lastId = userrepo.findLastId();
        if (lastId == null) {
            return "USR001";
        } else {
            int number = Integer.parseInt(lastId.substring(3)) + 1;
            String nextId = "USR" + String.format("%03d", number);
            return nextId;
        }
    }

    public List<UserBean> selectAlluser() {
        return userrepo.findAll().stream()
                .filter(user -> user.getDeleted() == null || !user.getDeleted()) // Filter out soft-deleted users
                .collect(Collectors.toList());
    }


    public Optional<UserBean> selectById(String id) {
        return userrepo.findById(id);
    }

    public void softDeleteUser(String id) {
        Optional<UserBean> userOptional = userrepo.findById(id);
        userOptional.ifPresent(user -> {
            user.setDeleted(true); // Set deleted flag to true
            userrepo.save(user);
        });
    }

    public List<UserBean> findNonDeletedUsers() {
        return userrepo.findNonDeletedUsers();
    }

}
