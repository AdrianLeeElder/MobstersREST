package com.adrian.mobsters.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document(collection = "accounts")
public class Account {

    @Id
    private final String id;
    @NotBlank
    private final String username;
    @NotBlank
    private final String phoneNumber;
}
