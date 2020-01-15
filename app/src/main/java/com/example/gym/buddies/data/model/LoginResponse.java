package com.example.gym.buddies.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private int userId;
    private String responseMessage;
    private String responseStatus;
    private int responseCode;
    private String jwtToken;
    private String userName;
}
