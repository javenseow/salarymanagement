package com.example.salarymanagement.controller;

import com.example.salarymanagement.helper.UserHelper;
import com.example.salarymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadUsers(@RequestParam("file") MultipartFile file) {
        String message;
        if (UserHelper.hasCSVFormat(file)) {
            userService.upload(file);
            try {
                userService.upload(file);
                message = "File upload successful: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body( "\" message \": \" " + message + " \"");
            } catch (Exception e) {
                message = "File upload unsuccessful: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body( "\" message \": \" " + message + " \"");
            }
        }

        message = "Please upload a CSV file";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "\" message \": \" " + message + " \"");
    }
}
