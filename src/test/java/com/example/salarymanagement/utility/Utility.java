package com.example.salarymanagement.utility;

import com.example.salarymanagement.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static String validId = "e0001";
    public static String invalidId = "e1000";

    public static Double defaultMinSalary = 0.0;
    public static Double defaultMaxSalary = 4000.0;
    public static Integer defaultOffset = 0;
    public static Integer defaultLimit = 0;

    public static Double validMinSalary = 3000.0;
    public static Double validMaxSalary = 2999.0;
    public static Integer validOffset = 1;
    public static Integer validLimit = 1;

    public static Double invalidSalary = -50.0;
    public static Integer invalidOffset = -1;
    public static Integer invalidLimit = -1;

    public static User validUser = new User("e0001",
            "hpotter",
            "Harry Potter",
            2500.0,
            LocalDate.parse("2001-11-16"));

    public static User invalidUser = new User("e1000",
            "hpotter",
            "Harry Potter",
            2500.0,
            LocalDate.parse("2001-11-16"));

    public static User invalidSalaryUser = new User("e0001",
            "hpotter",
            "Hatty Potter",
            -250.0,
            LocalDate.parse("2001-11-16"));

    public static User validUser2 = new User("e0002",
            "rwesley",
            "Ron Weasley",
            3000.50,
            LocalDate.parse("2001-11-16"));

    public static List<User> fullUserList = new ArrayList<>(List.of(validUser, validUser2));

    public static List<User> userListWithValidUser2 = new ArrayList<>(List.of(validUser2));

    public static List<User> userListWithValidUser = new ArrayList<>(List.of(validUser));
}
