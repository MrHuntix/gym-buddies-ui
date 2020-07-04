package com.example.gym.buddies.ui.profile.ui.matches.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.gym.buddies.R;

import lombok.Getter;

@Getter
public class MatchViewHolder extends ParentViewHolder {
    private TextView titleGymName;
    private TextView subTitleBranchDetails;
    private Button showLocation;
    private Button showProfile;

    public MatchViewHolder(View itemView) {
        super(itemView);
        this.titleGymName = itemView.findViewById(R.id.match_gym_name);
        this.subTitleBranchDetails = itemView.findViewById(R.id.match_branch_name);
        this.showLocation = itemView.findViewById(R.id.match_show_on_map);
        this.showProfile = itemView.findViewById(R.id.match_view_profile);
    }
}
