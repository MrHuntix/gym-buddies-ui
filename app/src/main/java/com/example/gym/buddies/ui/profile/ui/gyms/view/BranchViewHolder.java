package com.example.gym.buddies.ui.profile.ui.gyms.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.gym.buddies.R;

import lombok.Getter;

@Getter
public class BranchViewHolder extends ChildViewHolder {
    private TextView address;
    private TextView landmark;
    private TextView locality;
    private TextView state;
    private TextView pincode;
    private TextView primary;
    private TextView secondary;
    private Button buddyUp;


    public BranchViewHolder(@NonNull View itemView) {
        super(itemView);
        this.address = itemView.findViewById(R.id.branch_value_address);
        this.landmark = itemView.findViewById(R.id.branch_value_landmark);
        this.locality = itemView.findViewById(R.id.branch_value_locality);
        this.state = itemView.findViewById(R.id.branch_value_state);
        this.pincode = itemView.findViewById(R.id.branch_value_pincode);
        this.primary = itemView.findViewById(R.id.branch_value_primary);
        this.secondary = itemView.findViewById(R.id.branch_value_secondary);
        this.buddyUp = itemView.findViewById(R.id.find_match);
    }
}
