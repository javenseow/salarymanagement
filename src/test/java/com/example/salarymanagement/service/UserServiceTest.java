package com.example.salarymanagement.service;

import com.example.salarymanagement.helper.Response;
import com.example.salarymanagement.helper.UserHelper;
import com.example.salarymanagement.model.User;
import com.example.salarymanagement.repository.UserRepository;
import com.example.salarymanagement.utility.Utility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Mock
    private UserHelper userHelper;

    @InjectMocks
    private UserService userService;

    @Test
    void upload_shouldReturnTrueWhenValidCSVIsProvided() throws Exception {
        boolean result = userService.upload(Utility.csvFile);

        Mockito.verify(userRepository, Mockito.times(1))
                .saveAll(UserHelper.csvToUser(Utility.csvFile.getInputStream()));
        assertTrue(result);
    }

    @Test
    void upload_shouldReturnFalseWhenNoUpdateIsDone() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(Utility.fullUserList);
        boolean result = userService.upload(Utility.csvFile);

        Mockito.verify(userRepository, Mockito.times(1))
                .saveAll(UserHelper.csvToUser(Utility.csvFile.getInputStream()));
        assertFalse(result);
    }

    @Test
    void upload_shouldThrowExceptionWhenCSVWithDuplicateRowsIsProvided() throws Exception {
        Exception e = assertThrows(RuntimeException.class, () -> {
            userService.upload(Utility.csvDuplicateFile);
        });

        String expectedMessage = Response.DUPLICATE_ROW;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void upload_shouldThrowExceptionWhenNonCSVIsProvided() throws Exception {
        Exception e = assertThrows(RuntimeException.class, () -> {
            userService.upload(Utility.textFile);
        });

        String expectedMessage = Response.INVALID_CSV;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void getUsers_shouldReturnFullListWithDefaultParameters() {
        Mockito.when(userRepository.findAll()).thenReturn(Utility.fullUserList);
        List<User> result = userService.getUsers(Utility.defaultMinSalary, Utility.defaultMaxSalary, Utility.defaultOffset, Utility.defaultLimit);

        assertEquals(Utility.fullUserList, result);
    }

    @Test
    void getUsers_shouldThrowExceptionWithInvalidMinSalary() {
        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.getUsers(Utility.invalidSalary, Utility.defaultMaxSalary, Utility.defaultOffset, Utility.defaultLimit);
        });

        String expectedMessage = Response.INVALID_MIN_SALARY;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void getUsers_shouldThrowExceptionWithInvalidMaxSalary() {
        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.getUsers(Utility.defaultMinSalary, Utility.invalidSalary, Utility.defaultOffset, Utility.defaultLimit);
        });

        String expectedMessage = Response.INVALID_MAX_SALARY;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void getUsers_shouldThrowExceptionWithInvalidOffset() {
        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.getUsers(Utility.defaultMinSalary, Utility.defaultMaxSalary, Utility.invalidOffset, Utility.defaultLimit);
        });

        String expectedMessage = Response.INVALID_OFFSET;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void getUsers_shouldThrowExceptionWithInvalidLimit() {
        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.getUsers(Utility.defaultMinSalary, Utility.defaultMaxSalary, Utility.defaultOffset, Utility.invalidLimit);
        });

        String expectedMessage = Response.INVALID_LIMIT;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void getUser_shouldReturnUserWithExistingID() {
       Mockito.when(userRepository.findById(Utility.validId)).thenReturn(Optional.of(Utility.validUser));
       User result = userService.getUser(Utility.validId);

       assertEquals(Utility.validUser, result);
    }

    @Test
    void getUser_shouldThrowExceptionWithNonExistingID() {
        Mockito.when(userRepository.findById(Utility.validId)).thenReturn(Optional.empty());
        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.getUser(Utility.invalidId);
        });

        String expectedMessage = Response.NO_SUCH_EMPLOYEE;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void createUser_shouldCreateSuccessfullyWithValidUser() {
        Mockito.when(userRepository.findById(Utility.validId)).thenReturn(Optional.empty());
        userService.createUser(Utility.validUser);

        Mockito.verify(userRepository, Mockito.times(1)).save(Utility.validUser);
    }

    @Test
    void createUser_shouldThrowExceptionWithExistingID() {
        Mockito.when(userRepository.findById(Utility.validId)).thenReturn(Optional.of(Utility.validUser));

        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.createUser(Utility.validUser);
        });

        String expectedMessage = Response.EMPLOYEE_ID_EXISTS;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void createUser_shouldThrowExceptionWithInvalidSalary() {
        Mockito.when(userRepository.findById(Utility.validId)).thenReturn(Optional.empty());

        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.createUser(Utility.invalidSalaryUser);
        });

        String expectedMessage = Response.INVALID_SALARY;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void deleteUser_shouldDeleteSuccessfullyWithExistingID() {
        Mockito.when(userRepository.existsById(Utility.validId)).thenReturn(true);
        userService.deleteUser(Utility.validId);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Utility.validId);
    }

    @Test
    void deleteUser_shouldThrowExceptionWithNonExistingID() throws Exception {
        Mockito.when(userRepository.existsById(Utility.validId)).thenReturn(false);

        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.deleteUser(Utility.invalidId);
        });

        String expectedMessage = Response.NO_SUCH_EMPLOYEE;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void updateUser_shouldUpdateSuccessfullyWithValidIDAndUser() {
        Mockito.when(userRepository.existsById(Utility.validId)).thenReturn(true);
        userService.updateUser(Utility.validId, Utility.validUser);

        Mockito.verify(userRepository, Mockito.times(1)).save(Utility.validUser);
    }

    @Test
    void updateUser_shouldThrowExceptionWithInvalidIDButUser() {
        Mockito.when(userRepository.existsById(Utility.invalidId)).thenReturn(false);
        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.updateUser(Utility.invalidId, Utility.invalidUser);
        });

        String expectedMessage = Response.NO_SUCH_EMPLOYEE;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void updateUser_shouldThrowExceptionWithValidIDButDifferentIDUser() {
        Mockito.when(userRepository.existsById(Utility.validId)).thenReturn(true);
        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.updateUser(Utility.validId, Utility.invalidUser);
        });

        String expectedMessage = Response.DIFFERENT_ID;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void updateUser_shouldThrowExceptionWithValidIDButInvalidSalaryUser() {
        Mockito.when(userRepository.existsById(Utility.validId)).thenReturn(true);
        Exception e = assertThrows(IllegalStateException.class, () -> {
            userService.updateUser(Utility.validId, Utility.invalidSalaryUser);
        });

        String expectedMessage = Response.INVALID_SALARY;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }
}