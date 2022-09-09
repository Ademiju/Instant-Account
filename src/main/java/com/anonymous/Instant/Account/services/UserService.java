package com.anonymous.Instant.Account.services;

import com.anonymous.Instant.Account.datas.models.User;
import com.anonymous.Instant.Account.dtos.requests.LoginRequest;
import com.anonymous.Instant.Account.dtos.responses.UserResponse;

public interface UserService {
    UserResponse register(User user);
    String login(LoginRequest loginRequest);

    void deleteAll();
}
