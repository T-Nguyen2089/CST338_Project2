package com.example.shroudedhaven;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shroudedhaven.databinding.ActivityTrailsBinding;

import java.util.ArrayList;
import java.util.Locale;

import database.TrailsRepository;
import database.entities.Trails;

public class TrailsActivity extends AppCompatActivity {

    public static final String TRAILS_ACTIVITY_USER_ID = "com.example.shroudedhaven.TRAILS_ACTIVITY_USER_ID";

    private ActivityTrailsBinding binding;
    private TrailsRepository repository;
    public static final String TAG = "DAC_SHROUDED HAVEN";

    String mTrail = "";
    double mLength = 0.0;
    String mDescription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = TrailsRepository.getRepository(getApplication());

        binding.trailsListView.setMovementMethod(new ScrollingMovementMethod());
        updateDisplay();

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertTrailsRecord();
                updateDisplay();
            }
        });
    }

    private void insertTrailsRecord(){
        if(mTrail.isEmpty()){
            return;
        }
        Trails trails = new Trails(mTrail, mLength, mDescription);
        repository.insertTrails(trails);
    }

    private void updateDisplay(){
        ArrayList<Trails> allTrails = repository.getAllTrails();
        if(allTrails.isEmpty()){
            binding.trailsListView.setText(R.string.no_saved_trails);
        }
        StringBuilder sb = new StringBuilder();
        for(Trails trail : allTrails) {
            sb.append(trail);
        }
        binding.trailsListView.setText(sb.toString());
    }

    private void getInformationFromDisplay(){
        mTrail = binding.trailInputEditText.getText().toString();

        try {
            mLength = Double.parseDouble(binding.lengthInputEditText.getText().toString());
        }catch  (NumberFormatException e){
            Log.d(TAG, "Error reading value from Length edit text.");
        }

        mDescription = binding.descriptionEditTextView.getText().toString();
    }

    static Intent trailsActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, TrailsActivity.class);
        intent.putExtra(TRAILS_ACTIVITY_USER_ID, userId);
        return intent;
    }
}
