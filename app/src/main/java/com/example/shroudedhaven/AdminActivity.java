package com.example.shroudedhaven;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shroudedhaven.databinding.ActivityAdminBinding;
import com.example.shroudedhaven.databinding.ActivityMainBinding;

import database.TrailsRepository;

public class AdminActivity extends AppCompatActivity {
    private static final String ADMIN_ACTIVITY_USER_ID = "com.example.shroudedhaven.ADMIN_ACTIVITY_USER_ID";
    private ActivityAdminBinding binding;

    public static final String TAG = "DAC_HIKER";

    int loggedInUserId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO: When swapping to admin page, make sure to stay logged into USER

//        loginUser();
//
//        if(loggedInUserId ==-1){
//            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
//            startActivity(intent);
//        }


        binding.logOutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.adminTrails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, TrailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        loggedInUserId = getIntent().getIntExtra(ADMIN_ACTIVITY_USER_ID, -1);
    }

    static Intent adminActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ADMIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}