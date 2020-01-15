package com.example.gym.buddies.data.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class GymsResponse {
    private String responseMessage;
    private String responseStatus;
    private String responseCode;
    private List<Gyms> gyms;
}
