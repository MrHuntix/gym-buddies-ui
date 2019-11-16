package com.example.gym.buddies.data.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

public class SignUpModel {
    private String userName;
    private String emailId;
    private String mobileNumber;
    private String password;

    public SignUpModel() {
    }

    public SignUpModel(String userName, String emailId, String mobileNumber, String password) {
        this.userName = userName;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignUpModel that = (SignUpModel) o;
        return userName.equals(that.userName) &&
                emailId.equals(that.emailId) &&
                mobileNumber.equals(that.mobileNumber);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(userName, emailId, mobileNumber);
    }

    @Override
    public String toString() {
        return "SignUpModel{" +
                "userName='" + userName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
