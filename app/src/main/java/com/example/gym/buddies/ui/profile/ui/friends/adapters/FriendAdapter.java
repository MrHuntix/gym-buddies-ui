package com.example.gym.buddies.ui.profile.ui.friends.adapters;

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
import com.example.gym.buddies.data.protos.MatchLookupProto;
import com.example.gym.buddies.ui.profile.ui.friends.view.FriendViewHolder;
import com.example.gym.buddies.utils.IntentUtil;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder> {

    private Context context;
    private List<MatchLookupProto.Friend> friends;
    Gbuddies gbuddies;

    public FriendAdapter(Context context, List<MatchLookupProto.Friend> friends) {
        this.context = context;
        this.friends = friends;
        Log.d("logTag", "found " + friends.size() + " friends");
        gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.friend_card, viewGroup, false);
        return new FriendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder chatViewHolder, int i) {
        MatchLookupProto.Friend friend = friends.get(i);
        Log.d("logTag", "friend: " + friend);
        chatViewHolder.getGymDetails().setText(friend.getGym().getGymName() + ", " + friend.getGym().getBranch().getCity() + ", " + friend.getGym().getBranch().getLocality());
        chatViewHolder.getUserDetails().setText(friend.getUser().getUserName());
        chatViewHolder.getMessageButton().setOnClickListener(v -> {
            Log.d("logTag", "sending message to user " + friend.getUser().getUserName());
            Toast.makeText(context, "starting chat", Toast.LENGTH_LONG).show();
            IntentUtil.openWhatsAppConversationUsingUri(context, friend.getUser().getMobileNo(),
                    String.format(context.getString(R.string.message_body), friend.getUser().getUserName()));
        });

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
