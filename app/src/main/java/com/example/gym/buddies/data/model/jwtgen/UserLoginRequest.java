package com.example.gym.buddies.data.model.jwtgen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserLoginRequest {
    private String userName;
    private String password;
}
