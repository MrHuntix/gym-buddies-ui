package com.example.gym.buddies.ui.profile.ui.chats;

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
import com.example.gym.buddies.data.model.match.ChatResponse;
import com.example.gym.buddies.data.protos.MatchLookupProto;
import com.example.gym.buddies.ui.profile.ui.chats.adapters.ChatAdapter;
import com.example.gym.buddies.utils.SessionManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {

    private int userId;
    private ShimmerFrameLayout shimmerFrameLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        userId = SessionManager.getUserId(getContext());
        shimmerFrameLayout = root.findViewById(R.id.shimmer_chat_container);
        RecyclerView recyclerView = root.findViewById(R.id.chat_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        loadMatches(recyclerView);
        return root;
    }

    private void loadMatches(RecyclerView recyclerView) {
        Log.d("logTag", "deriving matches");
        Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
        Log.d("logTag", "sending request to get matches for chat");
        Call<MatchLookupProto.ChatResponse> response = gbuddies.getMatched(userId);

        response.enqueue(new Callback<MatchLookupProto.ChatResponse>() {
            @Override
            public void onResponse(Call<MatchLookupProto.ChatResponse> call, Response<MatchLookupProto.ChatResponse> response) {
                if(response!=null && response.body()!=null && response.isSuccessful()) {
                    List<MatchLookupProto.Match> derivedMatches = response.body().getMatchesList();
                    if (!CollectionUtils.isEmpty(derivedMatches)) {
                        Log.d("logTag", "derived " + derivedMatches.size() + " for userId: " + userId);
                        shimmerFrameLayout.stopShimmer();
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new ChatAdapter(getContext(), derivedMatches));
                    } else {
                        Toast.makeText(getContext(), "unable to find any possible matches", Toast.LENGTH_LONG).show();
                        Log.d("logTag", "no matches derived");
                        shimmerFrameLayout.stopShimmer();
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new ChatAdapter(getContext(), derivedMatches));
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchLookupProto.ChatResponse> call, Throwable t) {
                Log.d("logTag", "failed to make chat request with following exception");
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