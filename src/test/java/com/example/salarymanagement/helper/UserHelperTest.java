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
}