package com.anonymous.Instant.Account.services;

import com.anonymous.Instant.Account.datas.models.User;
import com.anonymous.Instant.Account.dtos.requests.LoginRequest;
import com.anonymous.Instant.Account.dtos.responses.UserResponse;
import com.anonymous.Instant.Account.exceptions.IncorrectDetailsException;
import com.anonymous.Instant.Account.exceptions.InstantAccountException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    User registerRequest;
    LoginRequest loginRequest;


    @BeforeEach
    void setUp() {
        registerRequest = User.builder().firstName("Test").lastName("User").email("test@mail.com").password("Test1#").confirmPassword("Test1#").build();
        loginRequest = LoginRequest.builder().email("test@mail.com").password("Test1#").build();
    }

    @Test
    void userCanCreateAccountTest(){
        UserResponse userResponse = userService.register(registerRequest);
        assertThat(userResponse.getEmail(),is(registerRequest.getEmail()));
        assertThat(userResponse.getMessage(),is("Account Successfully Created"));
    }
    @Test
    void createNewUser_WithExistingEmail_ThrowsExceptionTest() {
        userService.register(registerRequest);
        assertThatThrownBy(() -> userService.register(registerRequest)).isInstanceOf(InstantAccountException.class).hasMessage("Email Already Exist");

    }
    @Test
    void unMatchingPasswordAndConfirmPassword_ThrowExceptionTest(){
        registerRequest = User.builder().firstName("Test").lastName("User").email("test@mail.com").password("Test1#").confirmPassword("Test1").build();
        assertThatThrownBy(()->userService.register(registerRequest)).isInstanceOf(IncorrectDetailsException.class).hasMessage("Password Mismatch");

    }

    @Test
    void userCanLoginTest(){
        userService.register(registerRequest);
        String message = userService.login(loginRequest);
        assertThat(message, is("Successfully Logged In"));
    }
    @Test
    void unregisteredUserLogin_ThrowsExceptionTest(){
        loginRequest = LoginRequest.builder().email("notest@mail.com").password("Test1#").build();
        assertThatThrownBy(()->userService.login(loginRequest)).isInstanceOf(InstantAccountException.class).hasMessage("Incorrect login details");

    }
    @Test
    void userLoginWithIncorrectEmailThrowsExceptionTest(){
        userService.register(registerRequest);
        loginRequest = LoginRequest.builder().email("test@email.com").password("Test1#").build();
        assertThatThrownBy(()->userService.login(loginRequest)).isInstanceOf(InstantAccountException.class).hasMessage("Incorrect login details");
    }

    @Test
    void userLoginWithIncorrectPasswordThrowsExceptionTest(){
        userService.register(registerRequest);
        loginRequest = LoginRequest.builder().email("test@mail.com").password("Test1").build();
        assertThatThrownBy(()->userService.login(loginRequest)).isInstanceOf(IncorrectDetailsException.class).hasMessage("Incorrect login details");
    }

    @AfterEach

    void tearDown(){
        userService.deleteAll();
    }



}