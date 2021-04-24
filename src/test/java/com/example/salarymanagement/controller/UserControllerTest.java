package com.example.salarymanagement.controller;

import com.example.salarymanagement.helper.Response;
import com.example.salarymanagement.model.User;
import com.example.salarymanagement.repository.UserRepository;
import com.example.salarymanagement.service.UserService;
import com.example.salarymanagement.utility.Utility;
import org.hamcrest.Matchers;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void createUser_shouldReturnStatus201WithValidUser() throws Exception {
        User user = Utility.user1;

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.CREATE_SUCCESS)));
    }

    @Test
    void createUser_shouldReturnStatus400WithExistingId() throws Exception {
        User user1 = Utility.user1;
        Mockito.doThrow(new IllegalStateException(Response.EMPLOYEE_ID_EXISTS)).when(userService).createUser(user1);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user1.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.EMPLOYEE_ID_EXISTS)));
    }

    @Test
    void createUser_shouldReturnStatus400WithInvalidSalary() throws Exception {
        User invalidSalaryUser = Utility.invalidSalaryUser;
        Mockito.doThrow(new IllegalStateException(Response.INVALID_SALARY)).when(userService).createUser(invalidSalaryUser);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidSalaryUser.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.INVALID_SALARY)));
    }

    @Test
    void createUser_shouldReturnStatus400WithNonUniqueLogin() throws Exception {
        User user1 = Utility.user1;
        Mockito.doThrow(new RuntimeException(UserController.LOGIN)).when(userService).createUser(user1);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user1.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.LOGIN_NOT_UNIQUE)));
    }

}