package com.example.gym.buddies.ui.profile.ui.gyms.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.gym.buddies.R;

import lombok.Getter;

@Getter
public class GymViewHolder extends ParentViewHolder {
    private TextView name;
    private TextView website;
    private Button branches;
    private RecyclerView recyclerView;

    public GymViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.card_gym_name);
        this.website = itemView.findViewById(R.id.card_gym_website);
        this.branches = itemView.findViewById(R.id.card_gym_list_branches);
        this.recyclerView = itemView.findViewById(R.id.branch_list);
    }
}