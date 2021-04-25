package com.example.salarymanagement.helper;

import com.example.salarymanagement.model.User;
import com.example.salarymanagement.utility.Utility;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserHelperTest {

    @Test
    void hasCSVFormat_returnTrueWithCSVFile() {
        boolean result = UserHelper.hasCSVFormat(Utility.csvFile);
        assertTrue(result);
    }

    @Test
    void hasCSVFormat_returnFalseWithNonCSVFile() {
        boolean result = UserHelper.hasCSVFormat(Utility.textFile);
        assertFalse(result);
    }

    @Test
    void csvToUser_returnListOfUsersGivenValidInputStream() throws Exception {
        List<User> result = UserHelper.csvToUser(Utility.csvFile.getInputStream());

        assertEquals(Utility.fullUserList, result);
    }

    @Test
    void csvToUser_returnListOfValidUserTwoGivenFirstUserCommented() throws Exception {
        List<User> result = UserHelper.csvToUser(Utility.csvFirstLineCommentedFile.getInputStream());

        assertEquals(Utility.userListWithValidUser2, result);
    }

    @Test
    void csvToUser_throwExceptionGivenInvalidSalary() {
        Exception e = assertThrows(RuntimeException.class, () -> {
            UserHelper.csvToUser(Utility.csvInvalidSalaryFile.getInputStream());
        });

        String expectedMessage = Response.INVALID_SALARY;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void csvToUser_throwExceptionWithMissingColumn() {
        Exception e = assertThrows(RuntimeException.class, () -> {
            UserHelper.csvToUser(Utility.csvInvalidRowFile.getInputStream());
        });

        String expectedMessage = Response.INVALID_ROW;
        String actualMessage = e.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    void isValidSalary_returnTrueWhenSalaryIsMoreThanZero() {
        boolean result = UserHelper.isValidSalary(200.0);
        assertTrue(result);
    }

    @Test
    void isValidSalary_returnTrueWhenSalaryIsZero() {
        boolean result = UserHelper.isValidSalary(0.0);
        assertTrue(result);
    }

    @Test
    void isValidSalary_returnFalseWhenSalaryIsLesserZero() {
        boolean result = UserHelper.isValidSalary(-200.0);
        assertFalse(result);
    }

    @Test
    void isParamMoreThanEqualZero_returnTrueWhenParamIsMoreThanZero() {
        boolean result = UserHelper.isParamMoreThanEqualZero(2);
        assertTrue(result);
    }

    @Test
    void isParamMoreThanEqualZero_returnTrueWhenParamIsZero() {
        boolean result = UserHelper.isParamMoreThanEqualZero(0);
        assertTrue(result);
    }

    @Test
    void isParamMoreThanEqualZero_returnFalseWhenParamIsLesserThanZero() {
        boolean result = UserHelper.isParamMoreThanEqualZero(0);
        assertTrue(result);
    }

    @Test
    void processUsers_returnListWithSecondValidUserGivenMinSalary() {
        List<User> result = UserHelper.processUsers(Utility.fullUserList, Utility.validMinSalary, Utility.defaultMaxSalary, Utility.defaultOffset, Utility.defaultLimit);
        assertEquals(Utility.userListWithValidUser2, result);
    }

    @Test
    void processUsers_returnListWithFirstValidUserGivenMaxSalary() {
        List<User> result = UserHelper.processUsers(Utility.fullUserList, Utility.defaultMinSalary, Utility.validMaxSalary, Utility.defaultOffset, Utility.defaultLimit);
        assertEquals(Utility.userListWithValidUser, result);
    }

    @Test
    void processUsers_returnListWithSecondValidUserGivenValidOffset() {
        List<User> result = UserHelper.processUsers(Utility.fullUserList, Utility.defaultMinSalary, Utility.defaultMaxSalary, Utility.validOffset, Utility.defaultLimit);
        assertEquals(Utility.userListWithValidUser2, result);
    }

    @Test
    void processUsers_returnListWithFirstValidUserGivenValidLimit() {
        List<User> result = UserHelper.processUsers(Utility.fullUserList, Utility.defaultMinSalary, Utility.defaultMaxSalary, Utility.defaultOffset, Utility.validLimit);
        assertEquals(Utility.userListWithValidUser, result);
    }

    @Test
    void processUsers_returnOrderedListWithDefaultParameters() {
        List<User> result = UserHelper.processUsers(Utility.inverseFullUserList, Utility.defaultMinSalary, Utility.defaultMaxSalary, Utility.defaultOffset, Utility.defaultLimit);
        assertEquals(Utility.fullUserList, result);
    }
}