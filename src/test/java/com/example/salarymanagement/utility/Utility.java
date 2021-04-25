package com.example.salarymanagement.utility;

import com.example.salarymanagement.model.User;
import org.springframework.mock.web.MockMultipartFile;

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
    public static List<User> inverseFullUserList = new ArrayList<>(List.of(validUser2, validUser));

    public static List<User> userListWithValidUser2 = new ArrayList<>(List.of(validUser2));

    public static List<User> userListWithValidUser = new ArrayList<>(List.of(validUser));

    private static String csvContent = "id,login,name,salary,startDate\n" +
        "e0001,hpotter,Harry Potter,2500.0,16-Nov-01\n" +
        "e0002,rwesley,Ron Weasley,3000.50,2001-11-16";

    private static String csvFirstLineCommentedContent = "id,login,name,salary,startDate\n" +
            "#e0001,hpotter,Harry Potter,2500.0,16-Nov-01\n" +
            "e0002,rwesley,Ron Weasley,3000.50,2001-11-16";

    private static String csvDuplicateContent = "id,login,name,salary,startDate\n" +
            "e0001,hpotter,Harry Potter,1234.00,16-Nov-01\n" +
            "e0001,hpotter,Harry Potter,1234.00,16-Nov-01";

    private static String csvInvalidSalaryContent = "id,login,name,salary,startDate\n" +
            "e0002,rwesley,Ron Weasley,-3000.50,2001-11-16";

    private static String csvInvalidRowContent = "id,login,name,salary,startDate\n" +
            "e0002,rwesley,Ron Weasley,,2001-11-16";

    private static String csvNonNumberSalaryContent = "id,login,name,salary,startDate\n" +
            "e0002,rwesley,Ron Weasley,a,2001-11-16";

    private static String csvInvalidDateContent = "id,login,name,salary,startDate\n" +
            "e0002,rwesley,Ron Weasley,123.00,a";

    public static MockMultipartFile csvFile = new MockMultipartFile("file", "sample.txt", "text/csv", csvContent.getBytes());
    public static MockMultipartFile csvDuplicateFile = new MockMultipartFile("file", "sample.txt", "text/csv", csvDuplicateContent.getBytes());
    public static MockMultipartFile textFile = new MockMultipartFile("file", "sample.txt", "text/plain", "some xml".getBytes());
    public static MockMultipartFile csvInvalidSalaryFile = new MockMultipartFile("file", "sample.txt", "text/csv", csvInvalidSalaryContent.getBytes());
    public static MockMultipartFile csvFirstLineCommentedFile = new MockMultipartFile("file", "sample.txt", "text/csv", csvFirstLineCommentedContent.getBytes());
    public static MockMultipartFile csvInvalidRowFile = new MockMultipartFile("file", "sample.txt", "text/csv", csvInvalidRowContent.getBytes());
    public static MockMultipartFile csvNonNumberSalaryFile = new MockMultipartFile("file", "sample.txt", "text/csv", csvNonNumberSalaryContent.getBytes());
    public static MockMultipartFile csvInvalidDateFile = new MockMultipartFile("file", "sample.txt", "text/csv", csvInvalidDateContent.getBytes());

}
