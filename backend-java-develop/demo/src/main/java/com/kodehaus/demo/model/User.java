package com.kodehaus.demo.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;

import lombok.Data;

import java.util.List;

@Data
public class User {

    @DocumentId
    private String id;
    private String username;
    private String emailId;
    private String password;
    private Integer age;
    private List<String> roles;
    private double salary;
    private String currency;

    @ServerTimestamp
    private Timestamp createdAt;
}