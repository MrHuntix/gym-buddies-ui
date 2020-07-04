package com.example.gym.buddies.data.model.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private int id;

    private int lookupId;

    private int gymId;

    private int branchId;

    private int requester;

    private int requestee;
}
