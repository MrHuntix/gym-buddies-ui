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
import com.example.gym.buddies.data.model.operation.Branch;
import com.example.gym.buddies.data.model.operation.Gym;
import com.example.gym.buddies.data.model.operation.GymResponse;
import com.example.gym.buddies.ui.profile.ui.gyms.view.GymViewHolder;

import java.util.List;

public class GymAdapter extends RecyclerView.Adapter<GymViewHolder> {
    private GymResponse gymsResponse;
    private Context context;

    private int lastPosition = -1;

    public GymAdapter(GymResponse gymsResponse, Context context) {
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

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull GymViewHolder gymViewHolder, int i) {
        Gym gyms = gymsResponse.getGyms().get(i);
        Log.d("GymAdapter", "gym:" + gyms);
        gymViewHolder.getName().setText(gyms.getName());
        gymViewHolder.getWebsite().setText(gyms.getWebsite());
        gymViewHolder.getBranches().setOnClickListener((view) -> {
            //view.setTextDirection(3);
            boolean show =
                    toggleLayout(!gymsResponse.getGyms().get(i).isExpanded(), view, gymViewHolder.getRecyclerView(), gyms.getBranches(), Integer.valueOf(gyms.getId()));
            gymsResponse.getGyms().get(i).setExpanded(show);
        });
        animate(i, gymViewHolder);
    }

    @Override
    public int getItemCount() {
        if(gymsResponse == null || ( gymsResponse.getGyms() == null || gymsResponse.getGyms().size() == 0)) {
            Log.d("GymAdapter", "0 gyms present");
            return 0;
        }
        return gymsResponse.getGyms().size();
    }

    private void animate(int i, GymViewHolder gymViewHolder) {
        if (i > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.up_from_bottom);
            gymViewHolder.itemView.startAnimation(animation);
            lastPosition = i;
            gymViewHolder.itemView.setAnimation(animation);
        }
    }

    private boolean toggleLayout(boolean isExpanded, View v, RecyclerView layoutExpand, List<Branch> branches, int gymId) {
        Animations.toggleArrow(v, isExpanded);
        if (isExpanded) {
            Animations.expand(layoutExpand, branches, context, gymId);
        } else {
            Animations.collapse(layoutExpand);
        }
        return isExpanded;
    }

}
