package com.example.salarymanagement.helper;

import com.example.salarymanagement.model.User;
import com.example.salarymanagement.utility.Utility;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserHelperTest {

    @Test
    void hasCSVFormat_shouldReturnTrueWithCSVFile() {
        boolean result = UserHelper.hasCSVFormat(Utility.csvFile);
        assertTrue(result);
    }

    @Test
    void hasCSVFormat_shouldReturnFalseWithNonCSVFile() {
        boolean result = UserHelper.hasCSVFormat(Utility.textFile);
        assertFalse(result);
    }

    @Test
    void csvToUser_shouldReturnListOfUsersGivenValidInputStream() throws Exception {
        List<User> result = UserHelper.csvToUser(Utility.csvFile.getInputStream());

        assertEquals(Utility.fullUserList, result);
    }

    @Test
    void csvToUser_shouldReturnListOfValidUserTwoGivenFirstUserCommented() throws Exception {
        List<User> result = UserHelper.csvToUser(Utility.csvFirstLineCommentedFile.getInputStream());

        assertEquals(Utility.userListWithValidUser2, result);
    }

    @Test
    void csvToUser_shouldThrowExceptionGivenNegativeSalary() {
        Exception e = assertThrows(RuntimeException.class, () -> {
            UserHelper.csvToUser(Utility.csvInvalidSalaryFile.getInputStream());
        });

        String expectedMessage = Response.INVALID_SALARY;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void csvToUser_shouldThrowExceptionWithMissingColumn() {
        Exception e = assertThrows(RuntimeException.class, () -> {
            UserHelper.csvToUser(Utility.csvInvalidRowFile.getInputStream());
        });

        String expectedMessage = Response.INVALID_ROW;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void csvToUser_shouldThrowExceptionGivenNonNumberSalary() {
        Exception e = assertThrows(RuntimeException.class, () -> {
            UserHelper.csvToUser(Utility.csvNonNumberSalaryFile.getInputStream());
        });

        String expectedMessage = Response.INVALID_SALARY;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void csvToUser_shouldThrowExceptionGivenInvalidDate() {
        Exception e = assertThrows(RuntimeException.class, () -> {
            UserHelper.csvToUser(Utility.csvInvalidDateFile.getInputStream());
        });

        String expectedMessage = Response.INVALID_DATE;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void isValidSalary_shouldReturnTrueWhenSalaryIsMoreThanZero() {
        boolean result = UserHelper.isValidSalary(200.0);
        assertTrue(result);
    }

    @Test
    void isValidSalary_shouldReturnTrueWhenSalaryIsZero() {
        boolean result = UserHelper.isValidSalary(0.0);
        assertTrue(result);
    }

    @Test
    void isValidSalary_shouldReturnFalseWhenSalaryIsLesserZero() {
        boolean result = UserHelper.isValidSalary(-200.0);
        assertFalse(result);
    }

    @Test
    void isParamMoreThanEqualZero_shouldReturnTrueWhenParamIsMoreThanZero() {
        boolean result = UserHelper.isParamMoreThanEqualZero(2);
        assertTrue(result);
    }

    @Test
    void isParamMoreThanEqualZero_shouldReturnTrueWhenParamIsZero() {
        boolean result = UserHelper.isParamMoreThanEqualZero(0);
        assertTrue(result);
    }

    @Test
    void isParamMoreThanEqualZero_shouldReturnFalseWhenParamIsLesserThanZero() {
        boolean result = UserHelper.isParamMoreThanEqualZero(0);
        assertTrue(result);
    }

    @Test
    void processUsers_shouldReturnListWithSecondValidUserGivenMinSalary() {
        List<User> result = UserHelper.processUsers(Utility.fullUserList, Utility.validMinSalary, Utility.defaultMaxSalary, Utility.defaultOffset, Utility.defaultLimit);
        assertEquals(Utility.userListWithValidUser2, result);
    }

    @Test
    void processUsers_shouldReturnListWithFirstValidUserGivenMaxSalary() {
        List<User> result = UserHelper.processUsers(Utility.fullUserList, Utility.defaultMinSalary, Utility.validMaxSalary, Utility.defaultOffset, Utility.defaultLimit);
        assertEquals(Utility.userListWithValidUser, result);
    }

    @Test
    void processUsers_shouldReturnListWithSecondValidUserGivenValidOffset() {
        List<User> result = UserHelper.processUsers(Utility.fullUserList, Utility.defaultMinSalary, Utility.defaultMaxSalary, Utility.validOffset, Utility.defaultLimit);
        assertEquals(Utility.userListWithValidUser2, result);
    }

    @Test
    void processUsers_shouldReturnListWithFirstValidUserGivenValidLimit() {
        List<User> result = UserHelper.processUsers(Utility.fullUserList, Utility.defaultMinSalary, Utility.defaultMaxSalary, Utility.defaultOffset, Utility.validLimit);
        assertEquals(Utility.userListWithValidUser, result);
    }

    @Test
    void processUsers_shouldReturnOrderedListWithDefaultParameters() {
        List<User> result = UserHelper.processUsers(Utility.inverseFullUserList, Utility.defaultMinSalary, Utility.defaultMaxSalary, Utility.defaultOffset, Utility.defaultLimit);
        assertEquals(Utility.fullUserList, result);
    }
}