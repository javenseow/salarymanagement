package com.example.salarymanagement.service;

import com.example.salarymanagement.helper.Response;
import com.example.salarymanagement.model.User;
import com.example.salarymanagement.repository.UserRepository;
import com.example.salarymanagement.utility.Utility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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
    void updateUser() {
    }

    @Test
    void checkDuplicates() {
    }

    @Test
    void checkDataChanged(){
    }
}