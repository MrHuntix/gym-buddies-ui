package com.example.gym.buddies.ui.profile.ui.chats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.model.match.ChatResponse;
import com.example.gym.buddies.data.model.match.Match;
import com.example.gym.buddies.data.model.match.MatchLookup;
import com.example.gym.buddies.data.model.match.MatchResponse;
import com.example.gym.buddies.ui.profile.ui.chats.adapters.ChatAdapter;
import com.example.gym.buddies.ui.profile.ui.matches.adapters.MatchAdapter;
import com.example.gym.buddies.utils.SessionManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {

    private int userId;
    private ShimmerFrameLayout shimmerFrameLayout;
    List<ChatResponse> matches;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        userId = SessionManager.getUserId(getContext());
        matches = new ArrayList<>();
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
        Call<List<ChatResponse>> response = gbuddies.getMatched(userId);
        response.enqueue(new Callback<List<ChatResponse>>() {
            @Override
            public void onResponse(Call<List<ChatResponse>> call, Response<List<ChatResponse>> response) {
                List<ChatResponse> derivedMatches = response.body();
                if(derivedMatches!=null && !derivedMatches.isEmpty()) {
                    Log.d("logTag", "derived " + derivedMatches.size() + " for userId: " + userId);
                    shimmerFrameLayout.stopShimmer();
                    recyclerView.setVisibility(View.VISIBLE);
                    matches.addAll(derivedMatches);
                    recyclerView.setAdapter(new ChatAdapter(getContext(), matches));
                } else {
                    Toast.makeText(getContext(), "unable to find any possible matches", Toast.LENGTH_LONG).show();
                    Log.d("logTag", "no matches derived");
                    shimmerFrameLayout.stopShimmer();
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new ChatAdapter(getContext(), matches));
                }
            }

            @Override
            public void onFailure(Call<List<ChatResponse>> call, Throwable t) {
                Log.d("logTag", "no chat data found");
                Toast.makeText(getContext(), "NO MATCHES", Toast.LENGTH_LONG).show();
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