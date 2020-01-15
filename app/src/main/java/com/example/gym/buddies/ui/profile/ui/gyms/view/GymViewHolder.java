package com.example.gym.buddies.ui.profile.ui.gyms.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.gym.buddies.R;

import lombok.Getter;

@Getter
public class GymViewHolder extends ParentViewHolder {
    private TextView gymName;
    private TextView gymTime;
    private TextView gymDate;
    private Button branches;
    private RecyclerView recyclerView;

    public GymViewHolder(@NonNull View itemView) {
        super(itemView);
        this.gymName = itemView.findViewById(R.id.card_gym_name);
        this.gymDate = itemView.findViewById(R.id.card_gym_day);
        this.gymTime = itemView.findViewById(R.id.card_gym_time);
        this.branches = itemView.findViewById(R.id.card_gym_list_branches);
        this.recyclerView = itemView.findViewById(R.id.branch_list);
    }
}