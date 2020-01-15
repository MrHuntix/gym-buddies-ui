package com.example.gym.buddies.ui.profile.ui.browse;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class BrowseViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BrowseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}