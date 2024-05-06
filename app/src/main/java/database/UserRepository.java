package database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shroudedhaven.MainActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import database.entities.User;

public class UserRepository {
    private final UserDAO userDAO;

    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDataBase(application);
        this.userDAO = db.userDAO();
    }

    public void insertUser(User... user) {
        UserDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public static UserRepository getRepository(Application application) {
        Future<UserRepository> future = UserDatabase.databaseWriteExecutor.submit(
                new Callable<UserRepository>() {
                    @Override
                    public UserRepository call() throws Exception {
                        return new UserRepository(application);
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
        return userDAO.getUserByUsername(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }
}
