package com.example.shroudedhaven;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shroudedhaven.databinding.ActivityAdminBinding;
import com.example.shroudedhaven.databinding.ActivityMainBinding;

public class AdminActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.shroudedhaven.MAIN_ACTIVITY_USER_ID";
    private ActivityAdminBinding binding;

    public static final String TAG = "DAC_HIKER";

    int loggedInUserId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    }

    private void loginUser() {
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
    }

    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}