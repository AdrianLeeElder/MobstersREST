package com.adrian.mobsters.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Document
@Data
public class Mobster {

    @Id
    private final String id;
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
    @Transient
    private List<ActionJob> actionJobs;
}