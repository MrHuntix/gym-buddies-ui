package com.example.gym.buddies.ui.profile.ui.chats;

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

public class ChatFragment extends Fragment {

    private ChatViewModel chatViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel =
                ViewModelProviders.of(this).get(ChatViewModel.class);
        View root = inflater.inflate(R.layout.fragment_request, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        chatViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}