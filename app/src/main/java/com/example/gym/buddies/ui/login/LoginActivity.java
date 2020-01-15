package com.example.gym.buddies.ui.login;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gym.buddies.R;
import com.example.gym.buddies.data.PlaceHolder;
import com.example.gym.buddies.data.model.SignUpModel;
import com.example.gym.buddies.ui.profile.ProfileActivity;
import com.example.gym.buddies.utils.IntentUtil;
import com.example.gym.buddies.utils.SessionManager;

import java.util.logging.Logger;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class LoginActivity extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger("LoginActivity");

    private LoginViewModel loginViewModel;
    private SignUpModel signUpModel;
    private AlertDialog dialoge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        dialoge = createDialoge();

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        if(SessionManager.getLoggedStatus(getApplicationContext())) {
            startActivity(IntentUtil.getIntentForGymBuddies(getApplicationContext(), ProfileActivity.class));
        } else{

        }
        signUpModel = new SignUpModel();
        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            logger.info("login form state: "+loginFormState);
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser();
            }
            setResult(Activity.RESULT_OK);
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });
    }

    private void updateUiWithUser() {
        SessionManager.setLoggedIn(getApplicationContext(), true, PlaceHolder.loginResponse);
        Intent i = IntentUtil.getIntentForGymBuddies(this.getApplicationContext(), ProfileActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
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
        builder.setPositiveButton(R.string.positive_button, (dialog, id)-> {
            Toast.makeText(this.getApplicationContext(), "logging in", Toast.LENGTH_SHORT).show();
            final EditText userName= signUpView.findViewById(R.id.sign_up_username);
            final EditText email = signUpView.findViewById(R.id.sign_up_email);
            final EditText mobile = signUpView.findViewById(R.id.sign_up_mobile);
            final EditText password = signUpView.findViewById(R.id.sign_up_password);
            signUpModel.setUserName(userName.getText().toString());
            signUpModel.setEmailId(email.getText().toString());
            signUpModel.setMobileNumber(mobile.getText().toString());
            signUpModel.setPassword(password.getText().toString());
            Log.d("LoginActivity", signUpModel.toString());
        });

        builder.setNegativeButton(R.string.negative_button, (dialog,id)->{
            Toast.makeText(this.getApplicationContext(), "cancel logging in", Toast.LENGTH_SHORT).show();
        });

        return builder.create();
    }
}
