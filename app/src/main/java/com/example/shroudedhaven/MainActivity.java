package com.example.shroudedhaven;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shroudedhaven.databinding.ActivityMainBinding;

import database.TrailsRepository;
import database.UserRepository;
import database.entities.User;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.shroudedhaven.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.shroudedhaven.MAIN_ACTIVITY_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.shroudedhaven.MAIN_ACTIVITY_Saved_Instance_State_UserId_Key";
    private ActivityMainBinding binding;

    private UserRepository userRepository;

    public static final String TAG = "DAC_USER";

    private int loggedInUserId = -1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = UserRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        //User is not logged in at this point, go to login screen
        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        updateSharedPreference();


        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        binding.listOfTrails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrailsActivity.class);
                startActivity(intent);
            }
        });


        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void loginUser(Bundle savedInstanceState) {
        // check shared preference for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState
                .containsKey(SHARED_PREFERENCE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }
        LiveData<User> userObserver = userRepository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                MainActivity.this.user = user;
                if (user != null) {
                    updateUIBasedOnUserRole(user);
                }
            }
        });
    }

    private void updateUIBasedOnUserRole(User user) {
        if (user.isAdmin()) {
            binding.adminButton.setVisibility(View.VISIBLE);
            binding.adminButton.setEnabled(true);
            binding.adminButton.setOnClickListener(v -> navigateToAdminActivity());
        } else {
            binding.adminButton.setVisibility(View.GONE);
        }
        invalidateOptionsMenu();
    }

    private void navigateToAdminActivity() {
        Intent intent = AdminActivity.adminActivityIntentFactory(this, loggedInUserId);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if (user == null) {
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.sharedPreference_file_key),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), LOGGED_OUT);
        sharedPrefEditor.apply();
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}