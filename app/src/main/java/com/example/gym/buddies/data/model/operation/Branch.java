package com.example.gym.buddies.data.model.operation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Branch {
    private int id;
    private int gymId;
    private String locality;
    private String city;
    private float latitude;
    private float longitude;
    private String contact;
}
