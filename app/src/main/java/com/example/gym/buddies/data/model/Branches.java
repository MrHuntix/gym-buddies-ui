package com.example.gym.buddies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branches {
    private String id;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String locality;
    private String state;
    private String pincode;
    private String contactPrimary;
    private String contractSecondary;
}
