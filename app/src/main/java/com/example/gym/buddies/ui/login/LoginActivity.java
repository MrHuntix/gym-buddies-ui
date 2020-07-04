package com.example.gym.buddies.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.PlaceHolder;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.client.JwtGen;
import com.example.gym.buddies.data.model.jwtgen.UserLoginRequest;
import com.example.gym.buddies.data.model.jwtgen.UserLoginResponse;
import com.example.gym.buddies.data.model.jwtgen.UserSignupRequest;
import com.example.gym.buddies.data.model.jwtgen.UserSignupResponse;
import com.example.gym.buddies.ui.profile.ProfileActivity;
import com.example.gym.buddies.utils.IntentUtil;
import com.example.gym.buddies.utils.SessionManager;

import java.io.IOException;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The initial activity for login or signup
 * if user is logged in {@Link SessionManager} contains the jwt_token, user_name, user_id. On successful login the
 * activity is redirected to the profile activity which has various fragments
 */
public class LoginActivity extends AppCompatActivity {
    private static final Logger log = Logger.getLogger("apploggger");
    private AlertDialog dialoge;
    private EditText usernameEditText, passwordEditText;
    private Gbuddies gbuddies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialoge = createDialoge();
        gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
        log.info("started application");
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        //updateUiWithUser(PlaceHolder.loginResponse);
        if (SessionManager.getLoggedStatus(getApplicationContext())) {
            SessionManager.logInfo(getApplicationContext());
            log.info("already logged in: " + SessionManager.getUsername(getApplicationContext()));
            Intent i = IntentUtil.getIntentForGymBuddies(this.getApplicationContext(), ProfileActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    private void updateUiWithUser(UserLoginResponse response) {
        SessionManager.setLoggedIn(getApplicationContext(), true, response);
        Intent i = IntentUtil.getIntentForGymBuddies(this.getApplicationContext(), ProfileActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void handelSignup(View v) {
        dialoge.show();
    }

    private AlertDialog createDialoge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(R.string.sign_up_title);

        LayoutInflater inflater = this.getLayoutInflater();
        View signUpView = inflater.inflate(R.layout.activity_sign_up, null);
        builder.setView(signUpView);
        builder.setPositiveButton(R.string.positive_button, (dialog, id) -> {
            Toast.makeText(this.getApplicationContext(), "logging in", Toast.LENGTH_SHORT).show();
            final EditText userName = signUpView.findViewById(R.id.sign_up_username);
            final EditText email = signUpView.findViewById(R.id.sign_up_email);
            final EditText mobile = signUpView.findViewById(R.id.sign_up_mobile);
            final EditText password = signUpView.findViewById(R.id.sign_up_password);
            UserSignupRequest request = new UserSignupRequest(userName.getText().toString(), email.getText().toString()
                    , mobile.getText().toString(), password.getText().toString());
            Log.d("logTag","signupRequest " + request);
            Call<UserSignupResponse> response = gbuddies.signup(request);
            response.enqueue(new Callback<UserSignupResponse>() {
                @Override
                public void onResponse(Call<UserSignupResponse> call, Response<UserSignupResponse> response) {
                    UserSignupResponse responseBody = response.body();
                    Log.d("logTag", "response: " + responseBody);
                    Toast.makeText(getApplicationContext(), responseBody.getResponseMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<UserSignupResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "unable to signup", Toast.LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton(R.string.negative_button, (dialog, id) -> {
            Toast.makeText(this.getApplicationContext(), "cancel logging in", Toast.LENGTH_SHORT).show();
        });

        return builder.create();
    }

    public void handelLogin(View view) {
        log.info("button click");
        Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
        Call<UserLoginResponse> response = gbuddies.login(new UserLoginRequest(usernameEditText.getText().toString(), passwordEditText.getText().toString()));
        response.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                UserLoginResponse loginResponse = response.body();
                log.info("response: " + loginResponse.getUserName());
                updateUiWithUser(loginResponse);
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                t.printStackTrace();
                log.info("failed request call");
                showLoginFailed(t.getMessage());
            }
        });
    }
}
