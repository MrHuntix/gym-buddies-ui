package com.example.gym.buddies.ui.profile.ui.requests;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.protos.MatchLookupProto;
import com.example.gym.buddies.ui.profile.ui.requests.adapters.RequestAdapter;
import com.example.gym.buddies.utils.SessionManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFragment extends Fragment {

    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_request, container, false);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_request_container);
        RecyclerView recyclerView = root.findViewById(R.id.request_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        loadRequests(recyclerView);
        return root;
    }

    private void loadRequests(RecyclerView recyclerView) {
        Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
        Call<MatchLookupProto.FriendRequestsResponse> response = gbuddies.getFriendRequests(SessionManager.getUserId(getContext()));
        Log.d("logTag", "sending request to get friend requests");
        response.enqueue(new Callback<MatchLookupProto.FriendRequestsResponse>() {
            @Override
            public void onResponse(Call<MatchLookupProto.FriendRequestsResponse> call, Response<MatchLookupProto.FriendRequestsResponse> response) {
                if(response!=null && response.isSuccessful() && response.body()!=null) {
                    List<MatchLookupProto.FriendRequest> requests = response.body().getFriendRequestsList();
                    shimmerFrameLayout.stopShimmer();
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new RequestAdapter(requests, getContext()));
                }
            }

            @Override
            public void onFailure(Call<MatchLookupProto.FriendRequestsResponse> call, Throwable t) {
                Log.d("logTag", "failed to make request to fetch friend requests");
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
