package com.anonymous.Instant.Account.datas.repositories;

import com.anonymous.Instant.Account.datas.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByEmail(String email);
}
