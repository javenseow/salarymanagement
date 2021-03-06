package com.example.salarymanagement.helper;

import com.example.salarymanagement.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserHelper {
    public static String TYPE = "text/csv";
    public static String STRING = "string";
    public static String TEXT = "Text";

    /**
     * Checks if the file is of CSV format
     * @param file csv file
     * @return true if file is CSV, else returns false
     */
    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    /**
     * Converts CSV file to a list of user objects
     * @param is inputstream of csv file
     * @return list of user objects
     */
    public static List<User> csvToUser(InputStream is) {
        List<User> users = new ArrayList<>();

        // Date formatter for dd-MMM-yy and yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MMM-yy]" + "[yyyy-MM-dd]");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            // Ignore header
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                // Ignore line if line starts with #
                if(line.charAt(0) == '#') {
                    continue;
                }
                String[] values = line.split(",");

                // Throws exception if not all 5 columns are filled
                for (String value: values) {
                    if (value.equals("")) {
                        throw new IOException(Response.INVALID_ROW);
                    }
                }

                // Checks if salary is valid i.e >= 0
                if (isValidSalary(Double.parseDouble(values[3]))) {
                    users.add(new User(values[0], values[1], values[2], Double.parseDouble(values[3]), LocalDate.parse(values[4], formatter)));
                } else {
                    throw new IOException(Response.INVALID_SALARY);
                }
            }

            return users;
        } catch (Exception e) {
            String message = e.getMessage();

            if (message.contains(STRING)) {
                throw new RuntimeException(Response.INVALID_SALARY);
            } else if (message.contains(TEXT)) {
                throw new RuntimeException(Response.INVALID_DATE);
            }
            throw new RuntimeException(message);
        }
    }

    /**
     * Checks if salary is valid i.e more than or equal 0
     * @param salary salary of the user
     * @return true if salary is more than or equal 0, false otherwise
     */
    public static boolean isValidSalary(Double salary) {
        return salary >= 0;
    }

    /**
     * Checks if an Integer parameter is more than or equal to 0
     * @param parameter parameter to be checked
     * @return true if is more than or equal 0, false otherwise
     */
    public static boolean isParamMoreThanEqualZero(Integer parameter) {
        return parameter >= 0;
    }

    /**
     * Processes the user list based on the parameters given
     * @param users list of users
     * @param minSalary minSalary given by user, if null then 0
     * @param maxSalary maxSalary given by user, if null then 4000
     * @param offset offset given by user, if null then 0
     * @param limit limit given by user, if null then 0
     * @return list of users, sorted by id, based on the parameters given
     */
    public static List<User> processUsers(List<User> users, Double minSalary, Double maxSalary, Integer offset, Integer limit) {
        List<User> intermediateUsers = new ArrayList<>();
        List<User> finalUsers = new ArrayList<>();

        // Keep users whose salaries are within the min and max range
        for (User user : users) {
            Double salary = user.getSalary();
            if (salary >= minSalary && salary < maxSalary) {
                intermediateUsers.add(user);
            }
        }

        // Sort by employee id in ascending order
        Collections.sort(intermediateUsers, User.UserComparator);

        // Check if limit is 0, if yes then use full list size, else use limit as max size
        if (limit > 0) {
            for (int i = offset; i < limit; i++) {
            finalUsers.add(intermediateUsers.get(i));
            }
        } else {
            for (int i = offset; i < intermediateUsers.size(); i++) {
                finalUsers.add(intermediateUsers.get(i));
            }
        }

        return finalUsers;
    }
}
