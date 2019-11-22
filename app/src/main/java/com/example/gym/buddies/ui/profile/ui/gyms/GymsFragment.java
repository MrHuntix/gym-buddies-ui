package com.example.gym.buddies.ui.profile.ui.gyms;

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

public class GymsFragment extends Fragment {

    private GymsViewModel gymsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gymsViewModel =
                ViewModelProviders.of(this).get(GymsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gyms, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        gymsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}