package com.example.gym.buddies.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.client.ApiFactory;
import com.example.gym.buddies.data.client.Gbuddies;
import com.example.gym.buddies.data.protos.LoginSignupProto;
import com.example.gym.buddies.ui.profile.ProfileActivity;
import com.example.gym.buddies.utils.IntentUtil;
import com.example.gym.buddies.utils.SessionManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.protobuf.ByteString;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
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

    private AlertDialog dialog;
    private Gbuddies gbuddies;
    private Bitmap imageBitmap;
    private TextInputLayout username, email, contact, password, bio;
    private TextView loginLink;
    private Button createAccountButton, login;

    private ImageView profilePicture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initFields();
        log.info("started Login Activity");

        if (SessionManager.getLoggedStatus(getApplicationContext())) {
            SessionManager.logInfo(getApplicationContext());
            log.info("already logged in: " + SessionManager.getUsername(getApplicationContext()));
            Intent i = IntentUtil.getIntentForGymBuddies(this.getApplicationContext(), ProfileActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    private void updateUiWithUser(LoginSignupProto.LoginResponse response) {
        Toast.makeText(getApplicationContext(), response.getResponseMessage(), Toast.LENGTH_LONG).show();
        if(response.getResponseCode() != 400) {
            SessionManager.setLoggedIn(getApplicationContext(), true, response);
            Intent i = IntentUtil.getIntentForGymBuddies(this.getApplicationContext(), ProfileActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private AlertDialog createDialoge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//        builder.setTitle(R.string.sign_up_title);
        LayoutInflater inflater = this.getLayoutInflater();
        View signUpView = inflater.inflate(R.layout.activity_login, null);
        builder.setView(signUpView);
        builder.setPositiveButton(R.string.positive_button, (dialog, id) -> {
            Toast.makeText(this.getApplicationContext(), "logging in", Toast.LENGTH_SHORT).show();
            final TextInputLayout userName = signUpView.findViewById(R.id.login_username);
            final TextInputLayout password = signUpView.findViewById(R.id.login_password);
            Log.d("logTag", "logging in");
            handleLogin(Objects.requireNonNull(userName.getEditText()).getText().toString(), Objects.requireNonNull(password.getEditText()).getText().toString());
        });
        builder.setNegativeButton(R.string.negative_button, (dialog, id) -> Toast.makeText(this.getApplicationContext(), "login canceled", Toast.LENGTH_SHORT).show());
        return builder.create();
    }

    public void handleLogin(String username, String password) {
        log.info("handling login button click");
        LoginSignupProto.LoginRequest request = LoginSignupProto.LoginRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();
        Gbuddies gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);
        log.info("sending login request");
        Call<LoginSignupProto.LoginResponse> response = gbuddies.login(request);
        response.enqueue(new Callback<LoginSignupProto.LoginResponse>() {
            @Override
            public void onResponse(Call<LoginSignupProto.LoginResponse> call, Response<LoginSignupProto.LoginResponse> response) {
                if(response!=null && response.isSuccessful() && response.body()!=null) {
                    LoginSignupProto.LoginResponse loginResponse = response.body();
                    updateUiWithUser(loginResponse);
                }
            }

            @Override
            public void onFailure(Call<LoginSignupProto.LoginResponse> call, Throwable t) {
                log.info("exception occurred while sending login request");
                t.printStackTrace();
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
            profilePicture.setImageBitmap(imageBitmap);
        }
    }

    private byte[] getBytesFromBitmap(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        imageBitmap.recycle();
        return byteArray;
    }

    private void initFields() {
        this.username = findViewById(R.id.sign_up_username);
        this.email = findViewById(R.id.sign_up_email);
        this.contact = findViewById(R.id.sign_up_mobile);
        this.password = findViewById(R.id.sign_up_password);
        this.bio = findViewById(R.id.sign_up_bio);
        this.profilePicture = findViewById(R.id.profilePicture);
        this.createAccountButton = findViewById(R.id.createAccountButton);
        this.loginLink = findViewById(R.id.loginLink);
        this.createAccountButton.setOnClickListener(v -> createAccount());
        this.loginLink.setOnClickListener(v -> dialog.show());
        dialog = createDialoge();
        gbuddies = ApiFactory.gbuddies.create(Gbuddies.class);

    }

    private void createAccount() {
        LoginSignupProto.SignupRequest request = LoginSignupProto.SignupRequest.newBuilder()
                .setUserName(username.getEditText().getText().toString())
                .setEmailId(email.getEditText().getText().toString())
                .setMobileNo(contact.getEditText().getText().toString())
                .setPassword(password.getEditText().getText().toString())
                .setRoles(LoginSignupProto.RoleType.APP_USER)
                .setAbout(bio.getEditText().getText().toString())
                .setUserImage(ByteString.copyFrom(getBytesFromBitmap(imageBitmap)))
                .build();
        Log.d("logTag","sending request for signup");
        Call<LoginSignupProto.SignupResponse> response = gbuddies.signup(request);
        response.enqueue(new Callback<LoginSignupProto.SignupResponse>() {
            @Override
            public void onResponse(Call<LoginSignupProto.SignupResponse> call, Response<LoginSignupProto.SignupResponse> response) {
                if(response!=null && response.isSuccessful() && response.body()!=null) {
                    LoginSignupProto.SignupResponse responseBody = response.body();
                    Toast.makeText(getApplicationContext(), responseBody.getResponse().getResponseMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginSignupProto.SignupResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "unable to signup", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void takePicture(View view) {
        Log.d("logTag","opening camera to take picture");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
