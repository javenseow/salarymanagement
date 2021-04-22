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


    /**
     * [POST] To upload a CSV file of users
     * @param file
     * @return Response of 200 if upload is successful
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
}
