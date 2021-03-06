package com.example.gym.buddies.data.model.jwtgen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String userName;
    private String emailId;
    private String mobileNo;
    private String password;
    private String roles;
}
