package com.example.gym.buddies.ui.profile.ui.gyms;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class GymsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GymsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gyms/home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}