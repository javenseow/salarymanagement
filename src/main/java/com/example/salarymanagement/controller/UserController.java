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


}
