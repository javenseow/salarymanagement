package com.example.salarymanagement.service;

import com.example.salarymanagement.helper.Response;
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
     * @param file CSV file to be uploaded
     * @return true if there was an update done, false otherwise
     */
    public boolean upload(MultipartFile file) {
        boolean changed = false;
        try {
            if (UserHelper.hasCSVFormat(file)) {
                List<User> users = UserHelper.csvToUser(file.getInputStream());
                // Check if there are duplicate rows in the CSV file
                if (checkDuplicates(users)) {
                    throw new IOException(Response.DUPLICATE_ROW);
                }

                // Check if there is any data that needs to be created
                changed = checkDataChanged(users);

                userRepository.saveAll(users);
            } else {
                throw new IOException(Response.INVALID_CSV);
            }

            return changed;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Checks for duplicate rows in CSV given
     * @param users list of users to be checked
     * @return true if sizes are not the same, false otherwise
     */
    private boolean checkDuplicates(List<User> users) {
        Set<User> setOfUsers = new HashSet<>(users);
        return setOfUsers.size() < users.size();
    }

    /**
     * Checks if data was changed (updated or created) in the database
     * @param users list of users to be checked
     * @return true is data is changed, false otherwise
     */
    private boolean checkDataChanged(List<User> users) {
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

        return UserHelper.processUsers(allUsers, minSalary, maxSalary, offset, limit);
    }

    /**
     * Gets user based on id given
     * @param id employee id
     * @return user object
     */
    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(Response.NO_SUCH_EMPLOYEE));
    }

    /**
     * Creates a new user into the database
     * @param user employee to be added
     */
    public void createUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());

        // Check if user id is used
        if (userOptional.isPresent()) {
            throw new IllegalStateException(Response.EMPLOYEE_ID_EXISTS);
        }

        // Check if salary is valid i.e >= 0
        if (!UserHelper.checkSalary(user.getSalary())) {
            throw new IllegalStateException(Response.INVALID_SALARY);
        }

        userRepository.save(user);
    }

    /**
     * Deletes user based on id given
     * @param id employee id
     */
    public void deleteUser(String id) {
        // Check if employee exists
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException(Response.NO_SUCH_EMPLOYEE);
        }

        userRepository.deleteById(id);
    }

    /**
     * Updates the information of an existing user based on given id
     * @param id employee id
     * @param user employee details to be updated
     */
    public void updateUser(String id, User user) {
        // Check if employee exists
        userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(Response.NO_SUCH_EMPLOYEE));

        // Check if salary is valid i.e >= 0
        if (!UserHelper.checkSalary(user.getSalary())) {
            throw new IllegalStateException(Response.INVALID_SALARY);
        }

        userRepository.save(user);
    }
}
