package com.example.gym.buddies.data.model.match;

import com.example.gym.buddies.data.model.jwtgen.User;
import com.example.gym.buddies.data.model.operation.Branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatResponse {
    int match_id;
    int lookup_id;
    String gymName;
    String website;
    Branch branch;
    User user;
}
