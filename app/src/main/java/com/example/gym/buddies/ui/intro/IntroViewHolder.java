package com.example.gym.buddies.ui.intro;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.gym.buddies.R;

import lombok.Getter;

@Getter
class IntroViewHolder extends ParentViewHolder {
    private TextView header;
    private TextView subHeader;
    private TextView footer;
    private ImageView prev;
    private ImageView next;

    IntroViewHolder(View itemView) {
        super(itemView);
        this.header = itemView.findViewById(R.id.header);
        this.subHeader = itemView.findViewById(R.id.subHeader);
        this.footer = itemView.findViewById(R.id.footer);
        this.prev = itemView.findViewById(R.id.prev);
        this.next = itemView.findViewById(R.id.next);
    }
}
