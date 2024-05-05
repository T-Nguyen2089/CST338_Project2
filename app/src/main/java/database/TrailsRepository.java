package database;

import android.app.Application;
import android.util.Log;

import com.example.shroudedhaven.AdminActivity;
import com.example.shroudedhaven.TrailsActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import database.entities.Trails;

public class TrailsRepository {
    private TrailsDAO trailsDAO;
    private ArrayList<Trails> allLogs;
    private static TrailsRepository repository;

    private TrailsRepository(Application application){
        TrailsDatabase db = TrailsDatabase.getDatabase(application);
        this.trailsDAO = db.trailsDAO();
        this.allLogs = (ArrayList<Trails>) this.trailsDAO.getAllRecords();
    }

    public static TrailsRepository getRepository(Application application){
        if (repository != null){
            return repository;
        }
        Future<TrailsRepository> future = TrailsDatabase.databaseWriteExecutor.submit(
                new Callable<TrailsRepository>() {
                    @Override
                    public TrailsRepository call() throws Exception {
                        return new TrailsRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.d(TrailsActivity.TAG, "Problem getting TrailsRepository, thread error.");
        }
        return null;
    }

    public ArrayList<Trails> getAllTrails() {
        Future<ArrayList<Trails>> future = TrailsDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Trails>>() {
                    @Override
                    public ArrayList<Trails> call() throws Exception {
                        return (ArrayList<Trails>) trailsDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(AdminActivity.TAG, "Problem getting TrailsRepository, thread error.");
        }
        return null;
    }

    public void insertTrails(Trails trails){
        TrailsDatabase.databaseWriteExecutor.execute(() ->
        {
            trailsDAO.insert(trails);
        });
    }

//    public LiveData<Trails> getTrailsByTrailsName(String name) {
//        return TrailsDAO.getTrailsByTrailsName(name);
//    }


}
