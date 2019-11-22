package com.example.gym.buddies.ui.profile.ui.matches;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gym.buddies.R;

public class MatchesFragment extends Fragment {

    private MatchesViewModel matchesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        matchesViewModel =
                ViewModelProviders.of(this).get(MatchesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_matches, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        matchesViewModel.getText().observe(this, s -> textView.setText(s));
        return root;
    }
}