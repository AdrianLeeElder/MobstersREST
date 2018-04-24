package com.adrian.mobstersrest.mobsters.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Mobster {

    @Id
    @GeneratedValue
    private String id;
    private String username;
}
