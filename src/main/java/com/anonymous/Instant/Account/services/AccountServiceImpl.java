package com.anonymous.Instant.Account.services;

import com.anonymous.Instant.Account.datas.models.Account;
import com.anonymous.Instant.Account.datas.models.User;
import com.anonymous.Instant.Account.datas.repositories.AccountRepository;
import com.anonymous.Instant.Account.datas.repositories.UserRepository;
import com.anonymous.Instant.Account.dtos.responses.AccountResponse;
import com.anonymous.Instant.Account.exceptions.IncorrectDetailsException;
import com.anonymous.Instant.Account.exceptions.InstantAccountException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

public class AccountServiceImpl implements AccountService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;
    @Override
    public AccountResponse createAccount(String bvn, String email) throws UnirestException, ParseException {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post("https://fsi.ng/api/bvnr/GetSingleBVN")
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Sandbox-key", "oSvdabZHJpCUNLPRzyNxjbk3xVso1y1y1663015020")
                    .body(bvn)
                    .asString();

            if(response.getStatus()!=200) throw new IncorrectDetailsException("Incorrect BVN number");
            JSONParser parser = new JSONParser(response.getBody());
            JSONObject jsonObject = (JSONObject)parser.parse();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new InstantAccountException("User not found"));
            Account account = new Account();
            account.setBvn(bvn);
            String fullName = jsonObject.get("firstName").toString()+jsonObject.get("firstName").toString();
            account.setFullName(fullName);
            account.setContactAddress(jsonObject.get("contactAddress").toString());
            account.setEmail(email);
            account.setMobileNumber(jsonObject.get("phoneNumber").toString());
            String accountNumber = generateRandom(12);
            Optional<Account> accountFromRepository = accountRepository.findByAccountNumber(accountNumber);
            while(accountFromRepository.isPresent()){
                accountNumber = generateRandom(12);
            }
            account.setAccountNumber(accountNumber);
            account.setBalance(BigDecimal.ZERO);
            Account savedAccount = accountRepository.save(account);
            user.setAccount(savedAccount);
            userRepository.save(user);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setFullName(jsonObject.get("firstName").toString()+jsonObject.get("firstName").toString());
            accountResponse.setAccountNumber(accountNumber);
            return accountResponse;
    }
    public static String generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = '1';
        digits[1] = '1';
        for (int i = 2; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return new String(digits);
    }

    @Override
    public String makeDeposit(String accountNumber, int depositAmount) {
        Account accountFromRepository = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()-> new IncorrectDetailsException("Invalid account number"));
        BigDecimal accountBalance = accountFromRepository.getBalance();
        BigDecimal deposit = BigDecimal.valueOf(depositAmount);
        if(depositAmount < 0) throw new RuntimeException("Deposit Amount not valid");
        BigDecimal newAccountBalance = accountBalance.add(deposit);
        accountFromRepository.setBalance(newAccountBalance);
        accountRepository.save(accountFromRepository);
        String amount = String.valueOf(depositAmount);
        return accountNumber+"Successfully credited with "+amount;
    }
}
