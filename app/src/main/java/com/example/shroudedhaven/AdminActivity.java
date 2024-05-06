package com.example.shroudedhaven;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.shroudedhaven.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.shroudedhaven.MAIN_ACTIVITY_USER_ID";
    private ActivityAdminBinding binding;

    public static final String TAG = "DAC_USER";

    int loggedInUserId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        if (!initializeUser()) {
//            redirectToLogin();
//            return;
//        }
        setupListeners();


        binding.adminTrails.setOnClickListener(v ->{
                Intent intent = new Intent(AdminActivity.this, TrailsActivity.class);
                startActivity(intent);
        });

        binding.logOutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean initializeUser() {
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
        Log.d(TAG, "Logged in user ID: " + loggedInUserId);
        if (loggedInUserId == -1) {
            Log.e(TAG, "Failed to log in userId, redirecting to login");
            return false;
        }
        return true;
    }

    private void redirectToLogin() {
        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
        finish();
    }

    private void setupListeners() {
        binding.logOutAdmin.setOnClickListener(v -> {
            redirectToLogin();
        });

        binding.adminTrails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, TrailsActivity.class);
                startActivity(intent);
            }
        });
    }

    static Intent adminActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdminActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}