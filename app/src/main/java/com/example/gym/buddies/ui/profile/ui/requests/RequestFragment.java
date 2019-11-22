package com.example.gym.buddies.ui.profile.ui.requests;

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

public class RequestFragment extends Fragment {

    private RequestViewModel requestViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requestViewModel =
                ViewModelProviders.of(this).get(RequestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        requestViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}