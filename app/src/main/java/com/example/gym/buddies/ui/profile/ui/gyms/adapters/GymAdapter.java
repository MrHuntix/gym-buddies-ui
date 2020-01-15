package com.example.gym.buddies.ui.profile.ui.gyms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.gym.buddies.R;
import com.example.gym.buddies.animations.Animations;
import com.example.gym.buddies.data.model.Branches;
import com.example.gym.buddies.data.model.Gyms;
import com.example.gym.buddies.data.model.GymsResponse;
import com.example.gym.buddies.ui.profile.ui.gyms.view.GymViewHolder;

import java.util.List;

public class GymAdapter extends RecyclerView.Adapter<GymViewHolder> {
    private GymsResponse gymsResponse;
    private Context context;

    private int lastPosition = -1;
    public GymAdapter(GymsResponse gymsResponse, Context context) {
        this.gymsResponse = gymsResponse;
        this.context = context;
        Log.d("GymAdapter", "in GymAdapter");
    }

    @NonNull
    @Override
    public GymViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.gym_view, viewGroup, false);
        return new GymViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GymViewHolder gymViewHolder, int i) {
        Gyms gyms = gymsResponse.getGyms().get(i);
        gymViewHolder.getGymName().setText(gyms.getName());
        String time = gyms.getStartTime()+" to "+gyms.getEndTime();
        gymViewHolder.getGymTime().setText(time);
        String date = gyms.getStartDay()+" to "+gyms.getEndDay();
        gymViewHolder.getGymDate().setText(date);
        gymViewHolder.getBranches().setOnClickListener((view)->{
            boolean show = toggleLayout(!gymsResponse.getGyms().get(i).isExpanded(), view, gymViewHolder.getRecyclerView(), gyms.getBranches());
            gymsResponse.getGyms().get(i).setExpanded(show);
        });
        animate(i, gymViewHolder);
    }

    @Override
    public int getItemCount() {
        return gymsResponse.getGyms().size();
    }

    private void animate(int i, GymViewHolder gymViewHolder) {
        if(i > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.up_from_bottom);
            gymViewHolder.itemView.startAnimation(animation);
            lastPosition = i;
            gymViewHolder.itemView.setAnimation(animation);
        }
    }

    private boolean toggleLayout(boolean isExpanded, View v, RecyclerView layoutExpand, List<Branches> branches) {
        Animations.toggleArrow(v, isExpanded);
        if (isExpanded) {
            Animations.expand(layoutExpand, branches);
        } else {
            Animations.collapse(layoutExpand);
        }
        return isExpanded;
    }

}
