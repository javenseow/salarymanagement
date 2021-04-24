package com.example.salarymanagement.utility;

import com.example.salarymanagement.model.User;

import java.time.LocalDate;

public class Utility {
    public static String validId = "e0001";
    public static String invalidId = "e1000";

    public static User validUser = new User("e0001",
            "hpotter",
            "Harry Potter",
            2500.0,
            LocalDate.parse("2001-11-16"));

    public static User invalidSalaryUser = new User("e0001",
            "hpotter",
            "Hatty Potter",
            -250.0,
            LocalDate.parse("2001-11-16"));
}
