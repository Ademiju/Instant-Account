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
    public UserResponse register(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()) throw new InstantAccountException("Email Already Exist");
        if(!user.getPassword().equals(user.getConfirmPassword())) throw new IncorrectDetailsException("Password Mismatch");
        User instant_account_user = new User();
        modelMapper.map(user, instant_account_user);
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        String fullName = user.getFirstName()+" "+user.getLastName();
        userResponse.setFullName(fullName);
        userResponse.setMessage("Account Successfully Created");

        return userResponse;
    }

    @Override
    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new InstantAccountException("Incorrect login details"));
        if (!loginRequest.getPassword().equals(user.getPassword()))
            throw new IncorrectDetailsException("Incorrect login details");
        user.setLoggedIn(true);
        userRepository.save(user);
        return "Successfully Logged In";
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
