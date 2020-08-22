package com.example.gym.buddies.ui.profile.ui.other;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gym.buddies.R;

/**
 * Once a match request is made the user can see possible profiles with whom a request can be made.
 * TODO:
 * - A way to view possible buddies.
 * - A way to view requested buddies.
 * -
 */
public class OtherFragment extends Fragment {

    private OtherViewModel otherViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        otherViewModel =
                ViewModelProviders.of(this).get(OtherViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        return root;
    }
}