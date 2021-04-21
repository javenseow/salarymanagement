package com.example.salarymanagement.service;

import com.example.salarymanagement.helper.UserHelper;
import com.example.salarymanagement.model.User;
import com.example.salarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Uploads CSV file to repository, saving data into the database
     * @param file
     */
    public void upload(MultipartFile file) {
        try {
            List<User> users = UserHelper.csvToUser(file.getInputStream());
            if (checkDuplicates(users)) {
                throw new IOException("duplicate row");
            }
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    /**
     * Checks for duplicate rows in CSV given
     * @param users
     * @return true if sizes are not the same, false otherwise
     */
    private boolean checkDuplicates(List<User> users) {
        Set<User> setOfUsers = new HashSet<>(users);
        return setOfUsers.size() < users.size();
    }
}
