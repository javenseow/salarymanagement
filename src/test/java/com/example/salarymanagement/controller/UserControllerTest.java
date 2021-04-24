package com.example.salarymanagement.controller;

import com.example.salarymanagement.helper.Response;
import com.example.salarymanagement.helper.Results;
import com.example.salarymanagement.model.User;
import com.example.salarymanagement.service.UserService;
import com.example.salarymanagement.utility.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    
    @Test
    void uploadUsers_shouldReturnStatus400WhenCSVFileUploadedWithCreationOrUpdates() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "sample.txt", "text/csv", "some xml".getBytes());
        Mockito.when(userService.upload(file)).thenReturn(true);


        mvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", Matchers.is(Response.SUCCESS_WITH_CREATE_OR_UPDATE)));
    }

    @Test
    void uploadUsers_shouldReturnStatus400WhenCSVFileUploadedWithoutCreationOrUpdates() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "sample.txt", "text/csv", "some xml".getBytes());
        Mockito.when(userService.upload(file)).thenReturn(false);


        mvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", Matchers.is(Response.SUCCESS_WITHOUT_UPDATE)));
    }

    @Test
    void uploadUsers_shouldReturnStatus400WhenNonCSVFileUploaded() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "sample.txt", "text/plain", "some xml".getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/users/upload")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.is(Response.INVALID_CSV)));
    }

    @Test
    void fetchUsers_shouldReturnUsersWithoutParams() throws Exception {
        List<User> users = Utility.fullUserList;
        Double minSalary = 0.0;
        Double maxSalary = 4000.0;
        Integer offset = 0;
        Integer limit = 0;
        Mockito.when(userService.getUsers(minSalary, maxSalary, offset, limit)).thenReturn(users);

        MvcResult result = mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Results results = new Results(users);

        assertEquals(objectMapper.writeValueAsString(results), result.getResponse().getContentAsString());
    }

    @Test
    void fetchUsers_shouldReturnUsersWithMinSalary() throws Exception {
        List<User> users = Utility.userListWithValidUser2;

        Double minSalary = 3000.0;
        Double maxSalary = 4000.0;
        Integer offset = 0;
        Integer limit = 0;
        Mockito.when(userService.getUsers(minSalary, maxSalary, offset, limit)).thenReturn(users);

        MvcResult result = mvc.perform(get("/users/?minSalary=3000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Results results = new Results(users);

        assertEquals(objectMapper.writeValueAsString(results), result.getResponse().getContentAsString());
    }

    @Test
    void fetchUsers_shouldReturnUsersWithMaxSalary() throws Exception {
        List<User> users = Utility.userListWithValidUser;

        Double minSalary = 0.0;
        Double maxSalary = 2999.0;
        Integer offset = 0;
        Integer limit = 0;
        Mockito.when(userService.getUsers(minSalary, maxSalary, offset, limit)).thenReturn(users);

        MvcResult result = mvc.perform(get("/users/?maxSalary=2999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Results results = new Results(users);

        assertEquals(objectMapper.writeValueAsString(results), result.getResponse().getContentAsString());
    }

    @Test
    void fetchUsers_shouldReturnUsersWithOffset() throws Exception {
        List<User> users = Utility.userListWithValidUser2;

        Double minSalary = 0.0;
        Double maxSalary = 4000.0;
        Integer offset = 1;
        Integer limit = 0;
        Mockito.when(userService.getUsers(minSalary, maxSalary, offset, limit)).thenReturn(users);

        MvcResult result = mvc.perform(get("/users/?offset=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Results results = new Results(users);

        assertEquals(objectMapper.writeValueAsString(results), result.getResponse().getContentAsString());
    }

    @Test
    void fetchUsers_shouldReturnUsersWithLimit() throws Exception {
        List<User> users = Utility.userListWithValidUser;

        Double minSalary = 0.0;
        Double maxSalary = 4000.0;
        Integer offset = 0;
        Integer limit = 1;
        Mockito.when(userService.getUsers(minSalary, maxSalary, offset, limit)).thenReturn(users);

        MvcResult result = mvc.perform(get("/users/?limit=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Results results = new Results(users);

        assertEquals(objectMapper.writeValueAsString(results), result.getResponse().getContentAsString());
    }

    @Test
    void fetchUser_shouldReturnStatus200WithValidId() throws Exception {
        User validUser = Utility.validUser;
        Mockito.when(userService.getUser(Utility.validId)).thenReturn(validUser);

        MvcResult result = mvc.perform(get("/users/" + Utility.validId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(validUser.toString(), result.getResponse().getContentAsString());
    }

    @Test
    void fetchUser_shouldReturnStatus400WithNonExistingId() throws Exception {
        Mockito.doThrow(new IllegalStateException(Response.NO_SUCH_EMPLOYEE))
                .when(userService)
                .getUser(Utility.invalidId);

        mvc.perform(get("/users/" + Utility.invalidId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.NO_SUCH_EMPLOYEE)));

    }

    @Test
    void createUser_shouldReturnStatus201WithValidUser() throws Exception {
        User user = Utility.validUser;

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.CREATE_SUCCESS)));
    }

    @Test
    void createUser_shouldReturnStatus400WithExistingId() throws Exception {
        User user = Utility.validUser;
        Mockito.doThrow(new IllegalStateException(Response.EMPLOYEE_ID_EXISTS))
                .when(userService)
                .createUser(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.EMPLOYEE_ID_EXISTS)));
    }

    @Test
    void createUser_shouldReturnStatus400WithInvalidSalary() throws Exception {
        User invalidSalaryUser = Utility.invalidSalaryUser;
        Mockito.doThrow(new IllegalStateException(Response.INVALID_SALARY))
                .when(userService)
                .createUser(invalidSalaryUser);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidSalaryUser.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.INVALID_SALARY)));
    }

    @Test
    void createUser_shouldReturnStatus400WithNonUniqueLogin() throws Exception {
        User user = Utility.validUser;
        Mockito.doThrow(new RuntimeException(UserController.LOGIN))
                .when(userService)
                .createUser(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.LOGIN_NOT_UNIQUE)));
    }

    @Test
    void deleteUser_shouldReturnStatus200WithValidId() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(Utility.validId);

        mvc.perform(delete("/users/" + Utility.validId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.DELETE_SUCCESS)));
    }

    @Test
    void deleteUser_shouldReturnStatus200WithInvalidId() throws Exception {
        Mockito.doThrow(new IllegalStateException(Response.NO_SUCH_EMPLOYEE)).when(userService).deleteUser(Utility.invalidId);

        mvc.perform(delete("/users/" + Utility.invalidId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.NO_SUCH_EMPLOYEE)));
    }

    @Test
    void updateUser_shouldReturnStatus201WithValidUser() throws Exception {
        User user = Utility.validUser;

        mvc.perform(put("/users/" + Utility.validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.UPDATE_SUCCESS)));
    }

    @Test
    void updateUser_shouldReturnStatus400WithNonExistingId() throws Exception {
        User user = Utility.validUser;
        Mockito.doThrow(new IllegalStateException(Response.NO_SUCH_EMPLOYEE))
                .when(userService)
                .updateUser(Utility.invalidId, user);

        mvc.perform(put("/users/" + Utility.invalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.NO_SUCH_EMPLOYEE)));
    }

    @Test
    void updateUser_shouldReturnStatus400WithInvalidSalary() throws Exception {
        User invalidSalaryUser = Utility.invalidSalaryUser;
        Mockito.doThrow(new IllegalStateException(Response.INVALID_SALARY))
                .when(userService)
                .updateUser(Utility.validId, invalidSalaryUser);

        mvc.perform(put("/users/" + Utility.validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidSalaryUser.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.INVALID_SALARY)));
    }

    @Test
    void updateUser_shouldReturnStatus400WithNonUniqueLogin() throws Exception {
        User user = Utility.validUser;
        Mockito.doThrow(new RuntimeException(UserController.LOGIN))
                .when(userService)
                .updateUser(Utility.validId, user);

        mvc.perform(put("/users/" + Utility.validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is(Response.LOGIN_NOT_UNIQUE)));
    }
}