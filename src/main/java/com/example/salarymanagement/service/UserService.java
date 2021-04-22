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
import java.util.Optional;
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
     * @return true if there was an update done, false otherwise
     */
    public boolean upload(MultipartFile file) {
        try {
            List<User> users = UserHelper.csvToUser(file.getInputStream());
            if (checkDuplicates(users)) {
                throw new IOException("duplicate row");
            }

            boolean updates = checkDataCreated(users);

            userRepository.saveAll(users);

            return updates;
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

    /**
     * Checks if data was created in the database
     * @param users
     * @return true is data is created, false otherwise
     */
    private boolean checkDataCreated(List<User> users) {
        List<User> usersInDB = userRepository.findAll();

        return !users.equals(usersInDB);
    }

    /**
     * Returns a list of users according to the parameters given
     * @param minSalary default is 0
     * @param maxSalary default is 4000
     * @param offset default is 0
     * @param limit default 0
     * @return a list of users that fulfills the parameters given
     */
    public List<User> getUsers(Double minSalary, Double maxSalary, Integer offset, Integer limit) {
        List<User> allUsers = userRepository.findAll();
        List<User> finalUsers = UserHelper.processUsers(allUsers, minSalary, maxSalary, offset, limit);

        return finalUsers;
    }

    /**
     * Gets user based on id given
     * @param id employee id
     * @return user object
     */
    public User getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("no such employee"));

        return user;
    }

    /**
     * Creates a new user into the database
     * @param user employee to be added
     */
    public void createUser(User user) {
        Optional<User>userOptional = userRepository.findById(user.getId());

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Employee ID already exists");
        }

        userRepository.save(user);
    }

    /**
     * Deletes user based on id given
     * @param id employee id
     */
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("No such employee");
        }

        userRepository.deleteById(id);
    }
}
