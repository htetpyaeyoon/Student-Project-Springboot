package com.student.StudentRegisterwithSpringBoot.controller;

import com.student.StudentRegisterwithSpringBoot.Service.UserService;
import com.student.StudentRegisterwithSpringBoot.model.Role;
import com.student.StudentRegisterwithSpringBoot.model.UserBean;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Mock
    private MockHttpSession session;

    @Test
    public void loginForm() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("user"));

    }

    @Test
    public void register() throws Exception {
        this.mockMvc.perform(get("/registerform"))
                .andExpect(status().isOk())
                .andExpect(view().name("userRegister"))
                .andExpect(model().attributeExists("bean"));
    }

    @Test
    public void userRegister() throws Exception{
    UserBean user= new UserBean();
    user.setName("Htet");
    user.setEmail("h@gmail.com");
    user.setPassword("hpy123");
        this.mockMvc.perform(post("/register")
                        .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testUserRegisterInvalidData() throws Exception {
        UserBean user = new UserBean();
        user.setName("");
        this.mockMvc.perform(post("/register")
                        .flashAttr("user", user))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"));
    }


    @Test
    public void loginwithUser() throws Exception {
    UserBean user=new UserBean();
    user.setEmail("h@gmail.com");
    user.setPassword("111");
    user.setRole(Role.USER.name());
    when(userService.selectAlluser()).thenReturn(Collections.singletonList(user));
    this.mockMvc.perform(post("/login")
                    .flashAttr("user", user)
            .param("email","h@gmail.com")
            .param("password","111"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/userpage"));
    }

    @Test
    public void loginwithAdmin() throws Exception {
        UserBean user=new UserBean();
        user.setEmail("m@gmail.com");
        user.setPassword("123");
        user.setRole(Role.ADMIN.name());
        when(userService.selectAlluser()).thenReturn(Collections.singletonList(user));
        this.mockMvc.perform(post("/login")
                .flashAttr("user",user)
                .param("email","m@gamil.com")
                .param("password","123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminpage"));
    }

    @Test
    public void loginwithInvalidEmail() throws Exception{
        UserBean bean= new UserBean();
        bean.setEmail("a@gmail.com");
        bean.setPassword("000");
        this.mockMvc.perform(post("/login")
                        .flashAttr("user", bean))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("erroremail"));
    }

    @Test
    public void loginWithIncorrectPassword() throws Exception {
        // Mock the userService to return a list of users with a matching email but incorrect password
        List<UserBean> userList = new ArrayList<>();
        UserBean user = new UserBean();
        user.setEmail("ai@example.com");
        user.setPassword("correctpassword");
        userList.add(user);
        when(userService.selectAlluser()).thenReturn(userList);
        mockMvc.perform(post("/login")
                        .param("email", "ai@example.com")
                        .param("password", "incorrectpassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("errorpass"));
        verify(userService, times(1)).selectAlluser();
    }

    @Test
    public void adminhomewithIdNotInSession() throws Exception {
        when(session.getAttribute("adminId")).thenReturn(null);
        mockMvc.perform(get("/adminpage").session(session))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void adminhomewithIdinSession()throws Exception{
        when(session.getAttribute("adminId")).thenReturn("UST001");
        mockMvc.perform(get("/adminpage").session(session))
                .andExpect(status().is(200))
                .andExpect(view().name("adminpage"));
    }



    @Test
    public void userhomewithIdNotInSession() throws Exception {
        when(session.getAttribute("userId")).thenReturn(null);
        mockMvc.perform(get("/userpage").session(session))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void userhomewithIdinSession() throws Exception{
        when(session.getAttribute("userId")).thenReturn("USR001");
        mockMvc.perform(get("/userpage").session(session))
                .andExpect(status().is(200))
                .andExpect(view().name("userpage"));
    }

    @Test
    public void testUserManagement() throws Exception {
        List<UserBean> userList = new ArrayList<>();
        userList.add(new UserBean());
        userList.add(new UserBean());
        when(userService.selectAlluser()).thenReturn(userList);
        mockMvc.perform(get("/StudentRegisterwithSpringBoot/userlist"))
                .andExpect(status().isOk())  // Expect OK status
                .andExpect(view().name("userList"))
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attribute("list", userList));
    }

    @Test
    void userUpdateform() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void addUsers() {
    }

    @Test
    void userAdd() {
    }

    @Test
    void deleteStudent() {
    }
}