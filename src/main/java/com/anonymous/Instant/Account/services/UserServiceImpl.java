package com.anonymous.Instant.Account.services;

import com.anonymous.Instant.Account.datas.models.User;
import com.anonymous.Instant.Account.datas.repositories.UserRepository;
import com.anonymous.Instant.Account.dtos.requests.LoginRequest;
import com.anonymous.Instant.Account.dtos.responses.UserResponse;
import com.anonymous.Instant.Account.exceptions.IncorrectDetailsException;
import com.anonymous.Instant.Account.exceptions.InstantAccountException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public UserResponse register(User registerRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(registerRequest.getEmail());
        if(optionalUser.isPresent()) throw new InstantAccountException("Email Already Exist");
        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) throw new IncorrectDetailsException("Password Mismatch");
        User instant_account_user = new User();
        modelMapper.map(registerRequest, instant_account_user);
        userRepository.save(registerRequest);
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(registerRequest.getEmail());
        String fullName = registerRequest.getFirstName()+" "+ registerRequest.getLastName();
        userResponse.setFullName(fullName);
        userResponse.setMessage("Account Successfully Created");

        return userResponse;
    }

    @Override
    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new InstantAccountException("Incorrect login details"));
        if (!loginRequest.getPassword().equals(user.getPassword()))
            throw new IncorrectDetailsException("Incorrect login details");
        if(user.isLoggedIn()) throw new InstantAccountException("You are already logged in");
        user.setLoggedIn(true);
        userRepository.save(user);
        return "Successfully Logged In";
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
