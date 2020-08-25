package com.example.gym.buddies.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.wrappers.IntroModel;
import com.example.gym.buddies.ui.profile.ProfileActivity;
import com.example.gym.buddies.utils.IntentUtil;
import com.example.gym.buddies.utils.SessionManager;

import java.util.Arrays;

public class ActivityIntro extends AppCompatActivity {

    private RecyclerView introView;
    private Button prev;
    private Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        introView = findViewById(R.id.intro_view);
        if (SessionManager.getLoggedStatus(getApplicationContext())) {
            SessionManager.logInfo(getApplicationContext());
            Intent i = IntentUtil.getIntentForGymBuddies(this.getApplicationContext(), ProfileActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
            layoutManager.setSmoothScrollbarEnabled(false);
            introView.setLayoutManager(layoutManager);
            introView.setHasFixedSize(true);
            introView.setNestedScrollingEnabled(false);
            IntroAdapter adapter = new IntroAdapter(this.getApplicationContext(), Arrays.asList(
                    new IntroModel("TRANSFORM", "Your life", "Meet new people who are as driven as you are")
                    , new IntroModel("CREATE", "Your connections", "Meet new people who achieve their goals and start a routine with them")
                    , new IntroModel("PARTNER", "Your fitness goals", "Find the best partner near you to achieve your goals")
            ), layoutManager);
            introView.setAdapter(adapter);
        }

    }
}
