package com.example.gym.buddies.ui.profile.ui.friends;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.protos.MatchLookupProto;
import com.example.gym.buddies.ui.profile.ui.friends.adapters.FriendAdapter;
import com.example.gym.buddies.utils.SessionManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment {

    private int userId;
    private ShimmerFrameLayout shimmerFrameLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friend, container, false);
        userId = SessionManager.getUserId(getContext());
        shimmerFrameLayout = root.findViewById(R.id.shimmer_friend_container);
        RecyclerView recyclerView = root.findViewById(R.id.friend_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        loadMatches(recyclerView);
        return root;
    }

    private void loadMatches(RecyclerView recyclerView) {
        Log.d("logTag", "deriving matches");
        Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
        Log.d("logTag", "sending request to get friends");
        Call<MatchLookupProto.FriendResponse> response = gbuddies.getFriends(userId);

        response.enqueue(new Callback<MatchLookupProto.FriendResponse>() {
            @Override
            public void onResponse(Call<MatchLookupProto.FriendResponse> call, Response<MatchLookupProto.FriendResponse> response) {
                if(response!=null && response.body()!=null && response.isSuccessful()) {
                    List<MatchLookupProto.Friend> friends = response.body().getFriendsList();
                    if (!CollectionUtils.isEmpty(friends)) {
                        Log.d("logTag", "derived " + friends.size() + " for userId: " + userId);
                        shimmerFrameLayout.stopShimmer();
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new FriendAdapter(getContext(), friends));
                    } else {
                        Toast.makeText(getContext(), "unable to find any possible matches", Toast.LENGTH_LONG).show();
                        Log.d("logTag", "no matches derived");
                        shimmerFrameLayout.stopShimmer();
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new FriendAdapter(getContext(), friends));
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchLookupProto.FriendResponse> call, Throwable t) {
                Log.d("logTag", "request to get friends failed");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }
}