package com.example.gym.buddies.ui.profile.ui.gyms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.model.operation.GymResponse;
import com.example.gym.buddies.ui.profile.ui.gyms.adapters.GymAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GymsFragment extends Fragment {
    private GymResponse gymResponse;
    private ShimmerFrameLayout shimmerFrameLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gyms, container, false);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_container);
        RecyclerView recyclerView = root.findViewById(R.id.gyms_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        loadGyms(recyclerView);
        return root;
    }

    private void loadGyms(RecyclerView recyclerView) {
        Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
        Call<GymResponse> response = gbuddies.fetch();
        response.enqueue(new Callback<GymResponse>() {
            @Override
            public void onResponse(Call<GymResponse> call, Response<GymResponse> response) {
                gymResponse = response.body();
                shimmerFrameLayout.stopShimmer();
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView
                        .setAdapter(new GymAdapter(gymResponse, getContext()));
            }

            @Override
            public void onFailure(Call<GymResponse> call, Throwable t) {
                Log.d("aa", "onFailure: failure");
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