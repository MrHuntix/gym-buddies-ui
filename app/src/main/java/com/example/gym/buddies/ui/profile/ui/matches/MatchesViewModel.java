package com.example.gym.buddies.ui.profile.ui.matches;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MatchesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MatchesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is matches fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}