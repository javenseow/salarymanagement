package com.example.salarymanagement.controller;

import com.example.salarymanagement.helper.UserHelper;
import com.example.salarymanagement.model.User;
import com.example.salarymanagement.service.UserService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.fasterxml.jackson.databind.util.JSONPObject;
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

    @Autowired
    private UserService userService;


    /**
     * [POST] To upload a CSV file of users
     * @param file csv file
     * @return Response of 200 if upload is successful without updates, 201 if upload was successful with data creation/updates, else 400
     */
    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadUsers(@RequestParam("file") MultipartFile file) {
        String message;
        if (UserHelper.hasCSVFormat(file)) {
            try {
                if (userService.upload(file)) {
                    message = file.getOriginalFilename() + " upload successful";
                    return ResponseEntity.status(HttpStatus.CREATED).body("\" message \": \" " + message + "\"");
                } else {
                    message = file.getOriginalFilename() + " upload successful without updates";
                    return ResponseEntity.status(HttpStatus.OK).body("\" message \": \" " + message + "\"");
                }
            } catch (Exception e) {
                message = file.getOriginalFilename() + " upload unsuccessful: " + e.getMessage();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\" message \": \" " + message + " \"");
            }
        }

        message = "Please upload a CSV file";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "\" message \": \" " + message + " \"");
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

        return ResponseEntity.status(HttpStatus.OK).body("\" results \": " + results);
    }
}
