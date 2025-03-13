package com.student.StudentRegisterwithSpringBoot.Service;

import com.student.StudentRegisterwithSpringBoot.model.UserBean;
import com.student.StudentRegisterwithSpringBoot.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    UserService userService;

    @Test
    public void addUser() {
        UserBean user = new UserBean();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("123");
        when(userRepo.findLastId()).thenReturn("USR001");
        userService.adduser(user);
        verify(userRepo, times(1)).save(user);
        assertEquals("USR002", user.getId());
    }

    @Test
    public void generateId_whenLastIdIsNull(){
        when(userRepo.findLastId()).thenReturn(null);
        String userId = userService.generateNextUserId();
        assertEquals("USR001",userId);
    }

    @Test
    void update() {
        UserBean user = new UserBean();
        user.setId("USR001");
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("123");
        when(userRepo.save(user)).thenReturn(user);
        userService.update(user);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void selectAlluser() {
        List<UserBean> userList = new ArrayList<>();
        when(userRepo.findAll()).thenReturn(userList);
        List<UserBean> result = userService.selectAlluser();
        assertEquals(userList.size(), result.size());
    }

    @Test
    void selectById() {
        String userId = "USR001";
        UserBean user = new UserBean();
        user.setId(userId);
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        Optional<UserBean> result = userService.selectById(userId);
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
    }


    @Test
    void softDeleteUser() {
        String userId = "USR001";
        UserBean user = new UserBean();
        user.setId(userId);
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        userService.softDeleteUser(userId);
        assertTrue(user.getDeleted());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void findNonDeletedUsers() {
        List<UserBean> userList = new ArrayList<>();
        when(userRepo.findNonDeletedUsers()).thenReturn(userList);
        List<UserBean> result = userService.findNonDeletedUsers();
        assertEquals(userList.size(), result.size());
    }
}