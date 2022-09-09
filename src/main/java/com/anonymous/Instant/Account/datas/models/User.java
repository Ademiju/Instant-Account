package com.anonymous.Instant.Account.datas.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("Users")
public class User {
 @Id
 private String id;
 private String firstName;
 private String lastName;
 private String email;
 private String password;
 private String confirmPassword;
 private boolean isLoggedIn;
}
