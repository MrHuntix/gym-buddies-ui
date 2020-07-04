package com.example.gym.buddies.ui.profile.ui.matches.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.model.jwtgen.User;
import com.example.gym.buddies.data.model.match.MatchLookup;
import com.example.gym.buddies.data.model.match.MatchResponse;
import com.example.gym.buddies.data.model.operation.Branch;
import com.example.gym.buddies.ui.MapsActivity;
import com.example.gym.buddies.ui.profile.ui.matches.view.MatchViewHolder;
import com.example.gym.buddies.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchAdapter extends RecyclerView.Adapter<MatchViewHolder> {

    private Context context;
    private List<MatchLookup> possibleMatches;
    Gbuddies gbuddies;

    public MatchAdapter(Context context, List<MatchLookup> possibleMatches) {
        this.context = context;
        this.possibleMatches = possibleMatches;
        Log.d("logTag", "found " + possibleMatches.size() + " possible matches");
        gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.match_card, viewGroup, false);
        return new MatchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder matchViewHolder, int i) {
        MatchLookup matchLookup = possibleMatches.get(i);
        Log.d("logTag", "match: " + matchLookup);
        matchViewHolder.getTitleGymName().setText("GymName " + matchLookup.getGymId());
        matchViewHolder.getSubTitleBranchDetails().setText("branch: " + matchLookup.getBranchId());
        matchViewHolder.getShowLocation().setOnClickListener(v -> showOnMap(matchLookup.getBranchId())
        );
        matchViewHolder.getShowProfile().setOnClickListener(v -> {
            Log.d("logTag", "showing profile of user id " + matchLookup.getRequesterId());
            Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
            Call<User> response = gbuddies.getUserById(matchLookup.getRequesterId());

            response.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
//                    Log.d("logTag", user.toString());
//                    String userName = user.getUserName();
//                    Log.d("logTag", "user name" + userName);
                    createDialoge(user, matchLookup).show();
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("logTag", "exception while fetching data for user: " + matchLookup.getRequesterId());
                }
            });
        });
    }

    private AlertDialog createDialoge(User user, MatchLookup matchLookup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.view_profile_title);
        LayoutInflater inflater = LayoutInflater.from(context);
        View profileView = inflater.inflate(R.layout.profile_view, null);
        TextView userNameView = profileView.findViewById(R.id.popup_username);
        userNameView.setText(user.getUserName());
        builder.setView(profileView);
        builder.setPositiveButton(R.string.view_profile_like, (dialog, id) -> {
            Gbuddies factory = ApiFactory.gbuddies.create(Gbuddies.class);
            Call<MatchResponse> response = factory.like(matchLookup.getId(), SessionManager.getUserId(context));
            response.enqueue(new Callback<MatchResponse>() {
                @Override
                public void onResponse(Call<MatchResponse> call, Response<MatchResponse> response) {
                    MatchResponse body = response.body();
                    String responseMessage = body.getResponseMessage();
                    Toast.makeText(context, responseMessage, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<MatchResponse> call, Throwable t) {

                }
            });
            Toast.makeText(context, "buddy requested", Toast.LENGTH_SHORT).show();

        });

        builder.setNegativeButton(R.string.negative_button, (dialog, id) -> {
            Toast.makeText(context, "closing profile", Toast.LENGTH_SHORT).show();
        });

        return builder.create();
    }

    private void showOnMap(int branchId) {
        Log.d("logTag", "showing location on map for banch id: " + branchId);
        Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
        Call<Branch> response = gbuddies.coordinates(branchId);
        response.enqueue(new Callback<Branch>() {
            @Override
            public void onResponse(Call<Branch> call, Response<Branch> response) {
                Branch branch = response.body();
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("latitude", branch.getLatitude());
                intent.putExtra("longitude", branch.getLongitude());
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<Branch> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("logTag", "item count: " + possibleMatches.size());
        return possibleMatches.size();
    }
}
