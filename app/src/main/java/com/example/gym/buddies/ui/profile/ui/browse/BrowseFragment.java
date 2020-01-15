package com.example.gym.buddies.ui.profile.ui.browse;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gym.buddies.R;

public class BrowseFragment extends Fragment {

    private BrowseViewModel browseViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        browseViewModel =
                ViewModelProviders.of(this).get(BrowseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_browse, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        browseViewModel.getText().observe(this, textView::setText);
        return root;
    }
}