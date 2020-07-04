package com.example.gym.buddies.ui.profile.ui.other;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class OtherViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OtherViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is others fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}