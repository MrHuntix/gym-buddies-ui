package com.example.gym.buddies.ui.profile.ui.gyms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.client.GymMatcher;
import com.example.gym.buddies.data.model.match.MatchResponse;
import com.example.gym.buddies.data.model.operation.Branch;
import com.example.gym.buddies.ui.profile.ui.gyms.view.BranchViewHolder;
import com.example.gym.buddies.utils.SessionManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BranchAdapter extends RecyclerView.Adapter<BranchViewHolder> {
    private List<Branch> branches;
    private Context context;
    private int gymId;

    public BranchAdapter(List<Branch> branches, Context context, int gymId) {
        this.branches = branches;
        this.context = context;
        this.gymId = gymId;
        Log.d("logTag", "in BranchAdapter");
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.branch_card, viewGroup, false);
        return new BranchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder branchViewHolder, int i) {
        Branch branch = branches.get(i);
        branchViewHolder.getLocality().setText(branch.getLocality());
        branchViewHolder.getCity().setText(branch.getCity());
        branchViewHolder.getContact().setText(branch.getContact());
        branchViewHolder.getBuddyUp().setOnClickListener(v->{
            Gbuddies matcher = ApiFactory.gbuddies.create(Gbuddies.class);
            Log.d("logTag", "adding to lookup by user " + SessionManager.getUserId(context)+" for gym id " + gymId + ", branch: " + Integer.valueOf(branch.getId()));
            Toast.makeText(context, "added lookup. Check matches section in a few.", Toast.LENGTH_SHORT).show();
            Call<MatchResponse> response = matcher.addForLookup(SessionManager.getUserId(context), gymId, Integer.valueOf(branch.getId()));
            response.enqueue(new Callback<MatchResponse>() {
                @Override
                public void onResponse(Call<MatchResponse> call, Response<MatchResponse> response) {
                    MatchResponse message = response.body();
                    Log.d("logTag", "response: " + message);
                    Toast.makeText(context, message.getResponseMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<MatchResponse> call, Throwable t) {
                    Toast.makeText(context, "failed adding to lookup", Toast.LENGTH_SHORT).show();
                    Log.d("logTag", "failed adding to lookup by user " + SessionManager.getUserId(context)+" for gym id " + gymId + ", branch: " + Integer.valueOf(branch.getId()));
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return branches.size();
    }
}
