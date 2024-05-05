package com.example.shroudedhaven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shroudedhaven.databinding.ActivityLoginBinding;

import database.Repository;
import database.entities.User;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = Repository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }

    private void verifyUser() {

        String hikername = binding.userNameLoginEditText.getText().toString();
        if (hikername.isEmpty()) {
            Toast.makeText(this, "Username may not be blank.", Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> hikerObserver = repository.getUserByUsername(hikername);
        hikerObserver.observe(this, hiker -> {
            if (hiker != null){
                String password = binding.passwordLoginEditText.getText().toString();
                if(password.equals(hiker.getPassword())) {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(),hiker.getId()));
                }else{
                    toastMaker("Invalid password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            }else{
                toastMaker(String.format("No user %s is not a valid username.", hikername));
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