package com.example.gym.buddies.ui.profile.ui.requests.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
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
import com.example.gym.buddies.ui.profile.ui.requests.view.RequestViewHolder;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder> {

    private List<MatchLookupProto.FriendRequest> requests;
    private Context context;

    public RequestAdapter(List<MatchLookupProto.FriendRequest> requests, Context context) {
        this.requests = requests;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.request_card, viewGroup, false);
        return new RequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        MatchLookupProto.FriendRequest request = requests.get(position);
        holder.getUserName().setText(request.getUser().getUserName());
        holder.getUserImage().setImageBitmap(BitmapFactory.decodeByteArray(request.getUser().getUserImage().toByteArray(), 0, request.getUser().getUserImage().toByteArray().length));
        holder.getUserAbout().setText(request.getUser().getUserName());
//        holder.getGymDetails().setText(String.format(context.getString(R.string.request_gym), request.getGym().getGymName(), request.getGym().getBranch().getLocality()));
        holder.getGymDetails().setText(context.getString(R.string.request_gym, request.getGym().getGymName(), request.getGym().getBranch().getLocality()));
        holder.getUserAbout().setText(request.getUser().getAbout());
        holder.getAcceptRequest().setOnClickListener(v -> {
            Log.d("logTag", "accepting friend request");
            Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
            Call<MatchLookupProto.MatchResponse> response = gbuddies.acceptFriendRequest(request.getMatchRequestId());
            response.enqueue(new Callback<MatchLookupProto.MatchResponse>() {
                @Override
                public void onResponse(Call<MatchLookupProto.MatchResponse> call, Response<MatchLookupProto.MatchResponse> response) {
                    if(response!=null && response.isSuccessful() && response.body()!=null) {
                        MatchLookupProto.MatchResponse body = response.body();
                        Toast.makeText(context, body.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MatchLookupProto.MatchResponse> call, Throwable t) {
                    Log.d("logTag", "failed to accept friend request");
                    t.printStackTrace();
                }
            });
        });
        holder.getRejectRequest().setOnClickListener(v -> {
            Log.d("logTag", "rejecting friend request");
            Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
            Call<MatchLookupProto.MatchResponse> response = gbuddies.reject(request.getMatchRequestId());
            response.enqueue(new Callback<MatchLookupProto.MatchResponse>() {
                @Override
                public void onResponse(Call<MatchLookupProto.MatchResponse> call, Response<MatchLookupProto.MatchResponse> response) {
                    if(response!=null && response.isSuccessful() && response.body()!=null) {
                        MatchLookupProto.MatchResponse body = response.body();
                        Toast.makeText(context, body.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MatchLookupProto.MatchResponse> call, Throwable t) {
                    Log.d("logTag", "failed to reject friend request");
                    t.printStackTrace();
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(requests)?0:requests.size();
    }
}
