package com.example.shroudedhaven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shroudedhaven.databinding.ActivityLoginBinding;

import database.TrailsRepository;
import database.UserRepository;
import database.entities.User;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private UserRepository userRepository;
    private TrailsRepository trailsRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = UserRepository.getRepository(getApplication());
        trailsRepository = TrailsRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }

    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this, "Username may not be blank.", Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = userRepository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user != null){
                String password = binding.passwordLoginEditText.getText().toString();
                if(password.equals(user.getPassword())) {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(),user.getId()));
                }else{
                    toastMaker("Invalid password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            }else{
                toastMaker(String.format("No user %s is not a valid username.", username));
                binding.userNameLoginEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private void loginSuccessful(int userId) {
        Intent intent = AdminActivity.adminActivityIntentFactory(this, userId);
        startActivity(intent);
        finish();
    }
}