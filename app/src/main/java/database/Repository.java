package database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shroudedhaven.MainActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import database.entities.User;

public class Repository {
    private final UserDao userDao;

    public Repository(Application application) {
        DataBase db = DataBase.getDataBase(application);
        this.userDao = db.userDAO();
    }

    public void insertUser(User... user) {
        DataBase.databaseWriteExecutor.execute(() ->
        {
            userDao.insert(user);
        });
    }

    public static Repository getRepository(Application application) {
        Future<Repository> future = DataBase.databaseWriteExecutor.submit(
                new Callable<Repository>() {
                    @Override
                    public Repository call() throws Exception {
                        return new Repository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}
