package com.anonymous.Instant.Account.services;

import com.anonymous.Instant.Account.datas.models.User;
import com.anonymous.Instant.Account.dtos.requests.LoginRequest;
import com.anonymous.Instant.Account.dtos.responses.AccountResponse;
import com.anonymous.Instant.Account.dtos.responses.UserResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.tomcat.util.json.ParseException;

import java.net.http.HttpResponse;

public interface UserService {
    UserResponse register(User registerRequest);
    String login(LoginRequest loginRequest);


    void deleteAll();
}
