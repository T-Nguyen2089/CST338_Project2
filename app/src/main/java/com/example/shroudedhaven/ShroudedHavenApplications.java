package com.example.shroudedhaven;


import android.app.Application;

import database.TrailsDatabase;
import database.UserDatabase;

public class ShroudedHavenApplications extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        UserDatabase.getDatabase(this);
        TrailsDatabase.getDatabase(this);
    }
}
