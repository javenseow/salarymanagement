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
import java.util.List;

@Component
public class UserHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    // Converts values in CSV files to an arraylist of users
    public static List<User> csvToUser(InputStream is) {
        List<User> users = new ArrayList<>();

        // Date formatter for dd-MMM-yy and yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MMM-yy]" + "[yyyy-MM-dd]");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine(); // ignore header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                users.add(new User(values[0], values[1], values[2], Float.parseFloat(values[3]), LocalDate.parse(values[4], formatter)));
            }

            return users;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse CSV file: " + e.getMessage());
        }
    }
}
