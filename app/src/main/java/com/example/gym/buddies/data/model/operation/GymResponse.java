package com.example.gym.buddies.data.model.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class GymResponse {
    private String responseMessage;
    private String responseStatus;
    private int responseCode;
    private List<Gym> gyms;
}
