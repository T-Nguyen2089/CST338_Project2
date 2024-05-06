package com.example.shroudedhaven;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shroudedhaven.databinding.ActivityTrailsBinding;

import java.util.ArrayList;

import database.TrailsRepository;
import database.entities.Trails;

public class TrailsActivity extends AppCompatActivity {


    private static final String MAIN_ACTIVITY_USER_ID = "com.example.shroudedhaven.MAIN_ACTIVITY_USER_ID";

    private ActivityTrailsBinding binding;
    private TrailsRepository trailsRepository;
    public static final String TAG = "DAC_SHROUDED HAVEN";

    String mTrail = "";
    double mLength = 0.0;
    String mDescription = "";
    int loggedInUserId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
        if (loggedInUserId == -1) {
            redirectToLogin();
            return;
        }

        trailsRepository = TrailsRepository.getRepository(getApplication());

        binding.trailsListView.setMovementMethod(new ScrollingMovementMethod());

        updateDisplay();
//        setupListeners();
        binding.submitButton.setOnClickListener(v -> {
            getInformationFromDisplay();
            insertTrailsRecord();
            updateDisplay();
        });
    }

    private void redirectToLogin() {
        Intent intent = LoginActivity.loginIntentFactory(this);
        startActivity(intent);
        finish();
    }

    private void insertTrailsRecord() {
        if (mTrail.isEmpty()) {
            return;
        }
        Trails trails = new Trails(mTrail, mLength, mDescription);
        trailsRepository.insertTrails(trails);
    }


    private void updateDisplay() {
        ArrayList<Trails> allTrails = trailsRepository.getAllTrails();
        if (allTrails.isEmpty()) {
            binding.trailsListView.setText(R.string.no_saved_trails);
        }
        StringBuilder sb = new StringBuilder();
        for (Trails trail : allTrails) {
            sb.append(trail);
        }
        binding.trailsListView.setText(sb.toString());
    }

    private void getInformationFromDisplay() {
        mTrail = binding.trailInputEditText.getText().toString();

        try {
            mLength = Double.parseDouble(binding.lengthInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Length edit text.");
        }

        mDescription = binding.descriptionEditTextView.getText().toString();
    }

    static Intent trailsActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, TrailsActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}