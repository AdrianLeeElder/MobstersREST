package com.adrian.mobsters.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document
@Data
@NoArgsConstructor
public class Action {

    @Id
    private String id;
    @NotBlank
    private String name;
    private boolean running;
    private boolean complete;
    private boolean queued;

    public Action(String name) {
        this.name = name;
    }
}