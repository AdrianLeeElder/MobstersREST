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
    /**
     * The mobster account name.
     */
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
    /**
     * The authorized user this mobster belongs to.
     */
    private final String user;
    @Transient
    private List<ActionJob> actionJobs;
}