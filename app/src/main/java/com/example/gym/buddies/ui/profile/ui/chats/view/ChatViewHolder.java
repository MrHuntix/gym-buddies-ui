package com.example.gym.buddies.ui.profile.ui.chats.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.gym.buddies.R;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatViewHolder extends ParentViewHolder {
    private TextView gymDetails;
    private TextView userDetails;
    private Button messageButton;

    public ChatViewHolder(View itemView) {
        super(itemView);
        this.gymDetails = itemView.findViewById(R.id.chat_gym_name);
        this.userDetails = itemView.findViewById(R.id.chat_user_name);
        this.messageButton = itemView.findViewById(R.id.chat_message);
    }
}
