package com.example.gym.buddies.ui.profile.ui.gyms.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.model.Branches;
import com.example.gym.buddies.ui.profile.ui.gyms.view.BranchViewHolder;

import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchViewHolder> {
    private List<Branches> branches;

    public BranchAdapter(List<Branches> branches) {
        this.branches = branches;
        Log.d("GymAdapter", "in BranchAdapter");
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.branch_card, viewGroup, false);
        return new BranchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder branchViewHolder, int i) {
        Branches branch = branches.get(i);
        branchViewHolder.getAddress().setText(String.format("%s, %s", branch.getAddressLine1(), branch.getAddressLine2()));
        branchViewHolder.getLandmark().setText(branch.getLandmark());
        branchViewHolder.getLocality().setText(branch.getLocality());
        branchViewHolder.getState().setText(branch.getState());
        branchViewHolder.getPincode().setText(branch.getPincode());
        branchViewHolder.getPrimary().setText(branch.getContactPrimary());
        branchViewHolder.getSecondary().setText(branch.getContractSecondary());
        branchViewHolder.getBuddyUp().setOnClickListener(v->{
            Log.d("GymAdapter", "in BranchAdapter Click"+ branch.getId());
        });
    }

    @Override
    public int getItemCount() {
        return branches.size();
    }
}
