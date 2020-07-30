package com.example.gym.buddies.ui.profile.ui.chats.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.model.match.ChatResponse;
import com.example.gym.buddies.ui.profile.ui.chats.view.ChatViewHolder;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private Context context;
    private List<ChatResponse> possibleMatches;
    Gbuddies gbuddies;

    public ChatAdapter(Context context, List<ChatResponse> possibleMatches) {
        this.context = context;
        this.possibleMatches = possibleMatches;
        Log.d("logTag", "found " + possibleMatches.size() + " possible matches");
        gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chat_card, viewGroup, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {
        ChatResponse chatResponse = possibleMatches.get(i);
        Log.d("logTag", "chat: " + chatResponse);
        chatViewHolder.getGymDetails().setText(chatResponse.getGymName() + ", " + chatResponse.getBranch().getCity() + ", " + chatResponse.getBranch().getLocality());
        chatViewHolder.getUserDetails().setText(chatResponse.getUser().getUserName());
        chatViewHolder.getMessageButton().setOnClickListener(v -> {
            Log.d("logTag", "sending message to user " + chatResponse.getUser().getUserName());
            Toast.makeText(context, "starting chat", Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public int getItemCount() {
        return possibleMatches.size();
    }
}
