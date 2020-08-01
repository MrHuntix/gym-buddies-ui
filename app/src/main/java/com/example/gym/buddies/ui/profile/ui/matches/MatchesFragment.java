package com.example.gym.buddies.ui.profile.ui.matches;

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
import com.example.gym.buddies.data.model.match.MatchLookup;
import com.example.gym.buddies.data.protos.MatchLookupProto;
import com.example.gym.buddies.ui.profile.ui.matches.adapters.MatchAdapter;
import com.example.gym.buddies.utils.SessionManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_matches, container, false);
        userId = SessionManager.getUserId(getContext());
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
        Call<MatchLookupProto.LookupResponse> response = gbuddies.deriveMatches(userId);
        response.enqueue(new Callback<MatchLookupProto.LookupResponse>() {
            @Override
            public void onResponse(Call<MatchLookupProto.LookupResponse> call, Response<MatchLookupProto.LookupResponse> response) {
                if(response!=null && response.isSuccessful() && response.body()!=null) {
                    List<MatchLookupProto.MatchLookup> derivedMatches = response.body().getLookupsList();
                    if (!CollectionUtils.isEmpty(derivedMatches)) {
                        Log.d("logTag", "derived " + derivedMatches.size() + " for userId: " + userId);
                        shimmerFrameLayout.stopShimmer();
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new MatchAdapter(getContext(), derivedMatches));
                    } else {
                        Toast.makeText(getContext(), "unable to find any possible matches", Toast.LENGTH_LONG).show();
                        Log.d("logTag", "no matches derived");
                        shimmerFrameLayout.stopShimmer();
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new MatchAdapter(getContext(), derivedMatches));
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchLookupProto.LookupResponse> call, Throwable t) {
                Log.d("logTag", "request to derive matches failed");
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