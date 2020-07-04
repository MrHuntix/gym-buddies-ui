package com.example.gym.buddies.data.model.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchLookup {
    private int id;

    private int gymId;

    private int branchId;

    private int requesterId;

    private String status;
}
