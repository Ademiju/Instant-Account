package com.anonymous.Instant.Account.datas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("Accounts")
public class Account {
    private String id;
    private String fullName;
    private String email;
    private String contactAddress;
    private String bvn;
    private String mobileNumber;
    private String occupation;
    private String accountNumber;
    private BigDecimal balance;

}
