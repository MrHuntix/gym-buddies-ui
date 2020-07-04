package com.example.gym.buddies.ui.profile.ui.other;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        return root;
    }
}