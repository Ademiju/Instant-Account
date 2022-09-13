package com.anonymous.Instant.Account.services;

import com.anonymous.Instant.Account.dtos.responses.AccountResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.tomcat.util.json.ParseException;

public interface AccountService {
    AccountResponse createAccount(String bvn, String email) throws UnirestException, ParseException;
    String makeDeposit(String accountNumber, int depositAmount);
}
