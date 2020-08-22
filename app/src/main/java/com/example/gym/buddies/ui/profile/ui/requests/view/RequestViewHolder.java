package com.example.gym.buddies.ui.profile.ui.requests.view;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.gym.buddies.R;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestViewHolder extends ChildViewHolder {
    private ImageView userImage;
    private TextView userName;
    private TextView gymDetails;
    private TextView userAbout;
    private Button acceptRequest;
    private Button rejectRequest;

    public RequestViewHolder(View itemView) {
        super(itemView);
        this.userImage = itemView.findViewById(R.id.request_user_dp);
        this.userName = itemView.findViewById(R.id.request_user_name);
        this.gymDetails = itemView.findViewById(R.id.request_gym_detail);
        this.userAbout = itemView.findViewById(R.id.request_user_about);
        this.acceptRequest = itemView.findViewById(R.id.request_user_accept_button);
        this.rejectRequest = itemView.findViewById(R.id.request_user_reject_button);
    }
}
