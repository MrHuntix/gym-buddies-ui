package com.example.gym.buddies.data.model.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Gym{
    private int id;
    private String name;
    private String website;
    private boolean isExpanded = false;
    private List<Branch> branches = new ArrayList<>();

}
