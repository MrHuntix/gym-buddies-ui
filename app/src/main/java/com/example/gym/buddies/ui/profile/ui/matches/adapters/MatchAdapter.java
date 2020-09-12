package com.example.gym.buddies.ui.profile.ui.matches.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.model.jwtgen.User;
import com.example.gym.buddies.data.model.match.MatchLookup;
import com.example.gym.buddies.data.model.match.MatchResponse;
import com.example.gym.buddies.data.model.operation.Branch;
import com.example.gym.buddies.data.protos.GymProto;
import com.example.gym.buddies.data.protos.LoginSignupProto;
import com.example.gym.buddies.data.protos.MatchLookupProto;
import com.example.gym.buddies.ui.MapsActivity;
import com.example.gym.buddies.ui.profile.ui.matches.view.MatchViewHolder;
import com.example.gym.buddies.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchAdapter extends RecyclerView.Adapter<MatchViewHolder> {

    private Context context;
    private List<MatchLookupProto.MatchLookup> derivedMatches;
    Gbuddies gbuddies;

    public MatchAdapter(Context context, List<MatchLookupProto.MatchLookup> derivedMatches) {
        this.context = context;
        this.derivedMatches = derivedMatches;
        Log.d("logTag", "found " + derivedMatches.size() + " possible matches");
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
        MatchLookupProto.MatchLookup matchLookup = derivedMatches.get(i);
        Log.d("logTag", "match: " + matchLookup);
        matchViewHolder.getTitleGymName().setText(matchLookup.getGym().getGymName());
        matchViewHolder.getSubTitleBranchDetails().setText(matchLookup.getGym().getBranch().getLocality() + "-" + matchLookup.getGym().getBranch().getCity());
        matchViewHolder.getShowLocation()
                .setOnClickListener(v -> showOnMap(matchLookup.getGym().getBranch().getBranchId(), matchLookup.getGym().getBranch().getLatitude(), matchLookup.getGym().getBranch().getLongitude()));
        matchViewHolder.getShowProfile().setOnClickListener(v -> {
            Log.d("logTag", "showing profile of user id " + matchLookup.getUser().getUserId());
            createDialoge(matchLookup.getUser(), matchLookup, i).show();
        });
    }

    private AlertDialog createDialoge(MatchLookupProto.User user, MatchLookupProto.MatchLookup matchLookup, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.view_profile_title);
        LayoutInflater inflater = LayoutInflater.from(context);
        View profileView = inflater.inflate(R.layout.profile_view, null);
        TextView userNameView = profileView.findViewById(R.id.popup_username);
        userNameView.setText(user.getUserName());
        builder.setView(profileView);
        builder.setPositiveButton(R.string.view_profile_like, (dialog, id) -> {
            Gbuddies factory = ApiFactory.gbuddies.create(Gbuddies.class);
            Call<MatchLookupProto.MatchResponse> response = factory.like(matchLookup.getId(), SessionManager.getUserId(context));
            Log.d("logTag", "sending request for like");
            response.enqueue(new Callback<MatchLookupProto.MatchResponse>() {
                @Override
                public void onResponse(Call<MatchLookupProto.MatchResponse> call, Response<MatchLookupProto.MatchResponse> response) {
                    if(response!=null && response.body()!=null && response.isSuccessful()) {
                        MatchLookupProto.MatchResponse body = response.body();
                        Toast.makeText(context, body.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MatchLookupProto.MatchResponse> call, Throwable t) {
                    Log.d("logTag", "failed to send like request");
                    t.printStackTrace();
                }
            });
            this.derivedMatches.remove(position);
            notifyItemRemoved(position);
        });

        builder.setNegativeButton(R.string.negative_button, (dialog, id) -> {
            Toast.makeText(context, "closing profile", Toast.LENGTH_SHORT).show();
        });

        return builder.create();
    }

    private void showOnMap(int branchId, double latitude, double longitude) {
        Log.d("logTag", "showing location on map for banch id: " + branchId);
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        Log.d("logTag", "item count: " + derivedMatches.size());
        return derivedMatches.size();
    }
}
