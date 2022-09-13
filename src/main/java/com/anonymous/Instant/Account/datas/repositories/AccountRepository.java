package com.anonymous.Instant.Account.datas.repositories;

import com.anonymous.Instant.Account.datas.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
