package com.example.gym.buddies.ui.profile.ui.matches;

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
import com.example.gym.buddies.data.model.match.MatchLookup;
import com.example.gym.buddies.ui.profile.ui.matches.adapters.MatchAdapter;
import com.example.gym.buddies.utils.SessionManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * In this fragment the buddie with who you matched will be shown.
 * TODO:
 * - An options menu which manages various operations including unmatch, message etc.
 */
public class MatchesFragment extends Fragment {


    private int userId;
    private ShimmerFrameLayout shimmerFrameLayout;
    private List<MatchLookup> possibleMatches;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_matches, container, false);
        userId = SessionManager.getUserId(getContext());
        possibleMatches = new ArrayList<>();
        shimmerFrameLayout = root.findViewById(R.id.shimmer_match_container);
        RecyclerView recyclerView = root.findViewById(R.id.matches_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        loadMatches(recyclerView);
        return root;
    }

    private void loadMatches(RecyclerView recyclerView) {
        Log.d("logTag", "deriving matches");
        Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
        Call<List<MatchLookup>> response = gbuddies.deriveMatches(userId);
        response.enqueue(new Callback<List<MatchLookup>>() {
            @Override
            public void onResponse(Call<List<MatchLookup>> call, Response<List<MatchLookup>> response) {
                List<MatchLookup> derivedMatches = response.body();
                if(derivedMatches!=null && !derivedMatches.isEmpty()) {
                    Log.d("logTag", "derived " + derivedMatches.size() + " for userId: " + userId);
                    shimmerFrameLayout.stopShimmer();
                    recyclerView.setVisibility(View.VISIBLE);
                    possibleMatches.addAll(derivedMatches);
                    recyclerView.setAdapter(new MatchAdapter(getContext(), possibleMatches));
                } else {
                    Toast.makeText(getContext(), "unable to find any possible matches", Toast.LENGTH_LONG).show();
                    Log.d("logTag", "no matches derived");
                    shimmerFrameLayout.stopShimmer();
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new MatchAdapter(getContext(), possibleMatches));
                }
            }

            @Override
            public void onFailure(Call<List<MatchLookup>> call, Throwable t) {
                Log.d("logTag", "fail to derive matches");
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