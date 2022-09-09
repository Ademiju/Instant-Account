package com.anonymous.Instant.Account.controllers;

import com.anonymous.Instant.Account.datas.models.User;
import com.anonymous.Instant.Account.dtos.requests.LoginRequest;
import com.anonymous.Instant.Account.dtos.responses.ApiResponse;
import com.anonymous.Instant.Account.exceptions.IncorrectDetailsException;
import com.anonymous.Instant.Account.exceptions.InstantAccountException;
import com.anonymous.Instant.Account.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")

    public ResponseEntity<?> createUser(@RequestBody User registerRequest) {
        try {
            return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.CREATED);
        } catch (InstantAccountException | IncorrectDetailsException error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")

    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            return new ResponseEntity<>(userService.login(loginRequest),HttpStatus.FOUND);
        }catch (InstantAccountException  | IncorrectDetailsException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),HttpStatus.BAD_REQUEST);

        }
    }
}
