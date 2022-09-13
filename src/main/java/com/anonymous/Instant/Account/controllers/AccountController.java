package com.anonymous.Instant.Account.controllers;

import com.anonymous.Instant.Account.datas.models.User;
import com.anonymous.Instant.Account.dtos.responses.ApiResponse;
import com.anonymous.Instant.Account.exceptions.IncorrectDetailsException;
import com.anonymous.Instant.Account.exceptions.InstantAccountException;
import com.anonymous.Instant.Account.exceptions.MoneyException;
import com.anonymous.Instant.Account.services.AccountService;
import com.anonymous.Instant.Account.services.UserService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/create/{email}")

    public ResponseEntity<?> createAccount(@RequestBody String bvn, @PathVariable String email) {
        try {
            return new ResponseEntity<>(accountService.createAccount(bvn,email), HttpStatus.CREATED);
        } catch (InstantAccountException | IncorrectDetailsException | UnirestException | ParseException error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/deposit/{accountNumber}/{amount}")

    public ResponseEntity<?> makeDeposit(@PathVariable String accountNumber, @PathVariable int amount) {
        try {
            return new ResponseEntity<>(accountService.makeDeposit(accountNumber,amount), HttpStatus.CREATED);
        } catch (IncorrectDetailsException | MoneyException error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
