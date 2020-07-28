package com.example.gym.buddies.ui.profile.ui.gyms.view;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.gym.buddies.R;

import lombok.Getter;

@Getter
public class BranchViewHolder extends ChildViewHolder {
    private TextView locality;
    private TextView city;
    private TextView contact;
    private Button buddyUp;

    public BranchViewHolder(@NonNull View itemView) {
        super(itemView);
        this.locality = itemView.findViewById(R.id.branch_value_locality);
        this.city = itemView.findViewById(R.id.branch_value_city);
        this.contact = itemView.findViewById(R.id.branch_value_contact);
        this.buddyUp = itemView.findViewById(R.id.find_match);
    }
}
