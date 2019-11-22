package com.example.gym.buddies.ui.profile.ui.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class RequestViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RequestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is requests fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}