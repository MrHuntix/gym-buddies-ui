package com.example.gym.buddies.data.model.jwtgen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserLoginResponse {
    private int userId;
    private String responseMessage;
    private String responseStatus;
    private int responseCode;
    private String jwtToken;
    private String userName;
}
