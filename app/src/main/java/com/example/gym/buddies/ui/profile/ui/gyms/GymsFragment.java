package com.example.gym.buddies.ui.profile.ui.gyms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.protos.GymProto;
import com.example.gym.buddies.ui.profile.ui.gyms.adapters.GymAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GymsFragment extends Fragment {
    private GymProto.FetchResponse gymResponse;
    private ShimmerFrameLayout shimmerFrameLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gyms, container, false);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_gym_container);
        RecyclerView recyclerView = root.findViewById(R.id.gyms_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        loadGyms(recyclerView);
        return root;
    }

    private void loadGyms(RecyclerView recyclerView) {
        Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
        Call<GymProto.FetchResponse> response = gbuddies.fetch();
        Log.d("logTag", "sending fetch request");
        response.enqueue(new Callback<GymProto.FetchResponse>() {
            @Override
            public void onResponse(Call<GymProto.FetchResponse> call, Response<GymProto.FetchResponse> response) {
                if(response!=null && response.isSuccessful() && response.body()!=null) {
                    gymResponse = response.body();
                    shimmerFrameLayout.stopShimmer();
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView
                            .setAdapter(new GymAdapter(gymResponse, getContext()));
                }
            }

            @Override
            public void onFailure(Call<GymProto.FetchResponse> call, Throwable t) {
                Log.d("logTag", "failed to make fetch request");
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