package com.example.gym.buddies.ui.intro;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.wrappers.IntroModel;
import com.example.gym.buddies.ui.login.LoginActivity;
import com.example.gym.buddies.utils.IntentUtil;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroViewHolder> {

    private Context context;
    private List<IntroModel> introList;
    private LinearLayoutManager layoutManager;

    public IntroAdapter(Context context, List<IntroModel> introList, LinearLayoutManager layoutManager) {
        this.context = context;
        this.introList = introList;
        this.layoutManager = layoutManager;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        try {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.intro_template, parent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new IntroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        IntroModel message = introList.get(position);
        Log.d("logTag", message.toString());
        holder.getHeader().setText(message.getHeader());
        holder.getSubHeader().setText(message.getSubHeader());
        holder.getFooter().setText(message.getFooter());
        if(position == 0) {
            holder.getPrev().setVisibility(View.GONE);
        }

        holder.getPrev().setOnClickListener(v -> {
            Log.d("logTag", "prev:" + position);
            layoutManager.scrollToPosition(position - 1);
        });

        holder.getNext().setOnClickListener(v -> {
            Log.d("logTag", "next:" + position);
            if(position == this.getItemCount()-1) {
                Intent intent = IntentUtil.getIntentForGymBuddies(this.context, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                v.getContext().startActivity(intent);
            } else layoutManager.scrollToPosition(position + 1);

        });
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(introList)?0:introList.size();
    }
}
