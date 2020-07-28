package com.example.gym.buddies.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.model.jwtgen.UserLoginRequest;
import com.example.gym.buddies.data.model.jwtgen.UserLoginResponse;
import com.example.gym.buddies.data.model.jwtgen.UserSignupRequest;
import com.example.gym.buddies.data.model.jwtgen.UserSignupResponse;
import com.example.gym.buddies.data.protos.LoginSignupProto;
import com.example.gym.buddies.ui.profile.ProfileActivity;
import com.example.gym.buddies.utils.IntentUtil;
import com.example.gym.buddies.utils.SessionManager;
import com.google.protobuf.ByteString;

import java.io.ByteArrayOutputStream;
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
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private AlertDialog dialoge;
    private EditText usernameEditText, passwordEditText;
    private Gbuddies gbuddies;
    private Bitmap imageBitmap;

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

    private void updateUiWithUser(LoginSignupProto.LoginResponse response) {
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
        signUpView.findViewById(R.id.camera_button).setOnClickListener(v -> {
            Log.d("logTag","opening camera to take picture");
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
        builder.setView(signUpView);
        builder.setPositiveButton(R.string.positive_button, (dialog, id) -> {
            Toast.makeText(this.getApplicationContext(), "logging in", Toast.LENGTH_SHORT).show();
            final EditText userName = signUpView.findViewById(R.id.sign_up_username);
            final EditText email = signUpView.findViewById(R.id.sign_up_email);
            final EditText mobile = signUpView.findViewById(R.id.sign_up_mobile);
            final EditText password = signUpView.findViewById(R.id.sign_up_password);
            final EditText about = signUpView.findViewById(R.id.sign_up_bio);
            LoginSignupProto.SignupRequest request = LoginSignupProto.SignupRequest.newBuilder()
                    .setUserName(userName.getText().toString())
                    .setEmailId(email.getText().toString())
                    .setMobileNo(mobile.getText().toString())
                    .setPassword(password.getText().toString())
                    .setRoles(LoginSignupProto.RoleType.APP_USER)
                    .setAbout(about.getText().toString())
                    .setUserImage(ByteString.copyFrom(getBytesFromBitmap(imageBitmap)))
                    .build();
            Log.d("logTag","sending request for signup");
            Call<LoginSignupProto.SignupResponse> response = gbuddies.signup(request);
            response.enqueue(new Callback<LoginSignupProto.SignupResponse>() {
                @Override
                public void onResponse(Call<LoginSignupProto.SignupResponse> call, Response<LoginSignupProto.SignupResponse> response) {
                    if(response!=null && response.isSuccessful() && response.body()!=null) {
                        LoginSignupProto.SignupResponse responseBody = response.body();
                        Log.d("logTag", "response: " + responseBody);
                        Toast.makeText(getApplicationContext(), responseBody.getResponse().getResponseMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginSignupProto.SignupResponse> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(), "unable to signup", Toast.LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton(R.string.negative_button, (dialog, id) -> {
            Toast.makeText(this.getApplicationContext(), "signup canceled", Toast.LENGTH_SHORT).show();
        });

        return builder.create();
    }

    public void handelLogin(View view) {
        log.info("button click");
        LoginSignupProto.LoginRequest request = LoginSignupProto.LoginRequest.newBuilder()
                .setUsername(usernameEditText.getText().toString())
                .setPassword(passwordEditText.getText().toString())
                .build();
        Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);

        Call<LoginSignupProto.LoginResponse> response = gbuddies.login(request);
        log.info("starting login process");
        response.enqueue(new Callback<LoginSignupProto.LoginResponse>() {
            @Override
            public void onResponse(Call<LoginSignupProto.LoginResponse> call, Response<LoginSignupProto.LoginResponse> response) {
                if(response!=null && response.isSuccessful() && response.body()!=null) {
                    LoginSignupProto.LoginResponse loginResponse = response.body();
                    log.info("response message from server: " + loginResponse.getResponseMessage());
                    updateUiWithUser(loginResponse);
                }
            }

            @Override
            public void onFailure(Call<LoginSignupProto.LoginResponse> call, Throwable t) {
                t.printStackTrace();
                log.info("failed request call");
                showLoginFailed(t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
        }
    }

    private byte[] getBytesFromBitmap(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        imageBitmap.recycle();
        return byteArray;
    }
}
