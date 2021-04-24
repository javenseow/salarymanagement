package com.example.salarymanagement.controller;

import com.example.salarymanagement.helper.Response;
import com.example.salarymanagement.helper.Results;
import com.example.salarymanagement.model.User;
import com.example.salarymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    public static final String LOGIN = "LOGIN";

    @Autowired
    private UserService userService;

    /**
     * [POST] To upload a CSV file of users
     * @param file csv file
     * @return Response of 200 if upload is successful without updates, 201 if upload was successful with data creation/updates, else 400
     */
    @PostMapping(path = "/upload")
    public ResponseEntity<Response> uploadUsers(@RequestParam("file") MultipartFile file) {
        Response response;
        try {
            if (userService.upload(file)) {
                response = new Response(Response.SUCCESS_WITH_CREATE_OR_UPDATE);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response = new Response(Response.SUCCESS_WITHOUT_UPDATE);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            response = new Response(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * [GET] To get a list of user based on parameters given by user i.e minSalary, maxSalary, offset and limit
     * @param minSalary minSalary given by user, if null then 0
     * @param maxSalary maxSalary given by user, if null then 4000
     * @param offset offset given by user, if null then 0
     * @param limit limit given by user, if null then 0
     * @return Response of 200 with list of user
     */
    @GetMapping
    public ResponseEntity<?> fetchUsers(@RequestParam(required = false) Double minSalary, @RequestParam(required = false) Double maxSalary, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
        if (minSalary == null) {
            minSalary = 0.0;
        }

        if (maxSalary == null) {
            maxSalary = 4000.0;
        }

        if (offset == null) {
            offset = 0;
        }

        if (limit == null) {
            limit = 0;
        }

        List<User> results = userService.getUsers(minSalary, maxSalary, offset, limit);

        Results responseResults = new Results(results);
        return ResponseEntity.status(HttpStatus.OK).body(responseResults);
    }

    /**
     * [GET] Fetches an employee based on the given id
     * @param id employee id
     * @return response 200 if id exists
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<?> fetchUser(@PathVariable String id) {
        User user;
        Response response;
        try {
            user = userService.getUser(id);
        } catch (Exception e) {
            response = new Response(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * [POST] Creates a new user into the database
     * @param user employee to be added
     * @return response 200 if added
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Response response;
        try {
            userService.createUser(user);
        } catch (Exception e) {
            String errorMessage = e.getMessage();

            if (errorMessage.contains(LOGIN)) {
                response = new Response(Response.LOGIN_NOT_UNIQUE);
            } else {
                response = new Response(errorMessage);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response = new Response(Response.CREATE_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * [DELETE] Deletes an employee based on the given id
     * @param id employee id
     * @return response 200 if user exists
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable String id) {
        Response response;
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            response = new Response(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response = new Response(Response.DELETE_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * [PUT] Updates the user information of an existing user
     * @param id employee id
     * @param user user information
     * @return response 200 if update was successful
     */
    @PutMapping(path = "{id}")
    public ResponseEntity<Response> updateUser(@PathVariable String id, @RequestBody User user) {
        Response response;
        try {
            userService.updateUser(id, user);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains(LOGIN)) {
                response = new Response(Response.LOGIN_NOT_UNIQUE);
            } else {
                response = new Response(errorMessage);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response = new Response(Response.UPDATE_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
