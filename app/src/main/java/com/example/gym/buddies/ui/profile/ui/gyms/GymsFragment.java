package com.example.gym.buddies.ui.profile.ui.gyms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.PlaceHolder;
import com.example.gym.buddies.data.wrappers.GymWrapper;
import com.example.gym.buddies.ui.profile.ui.gyms.adapters.GymAdapter;

public class GymsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gyms, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.gyms_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView
                .setAdapter(new GymAdapter(GymWrapper.getGymResponseFromJson(PlaceHolder.gymResponse), getContext()));
        return root;
    }
}