package com.example.gym.buddies.ui.profile.ui.friends.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.gym.buddies.R;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendViewHolder extends ParentViewHolder {
    private TextView gymDetails;
    private TextView userDetails;
    private Button messageButton;

    public FriendViewHolder(View itemView) {
        super(itemView);
        this.gymDetails = itemView.findViewById(R.id.friend_gym_name);
        this.userDetails = itemView.findViewById(R.id.friend_user_name);
        this.messageButton = itemView.findViewById(R.id.friend_message);
    }
}
