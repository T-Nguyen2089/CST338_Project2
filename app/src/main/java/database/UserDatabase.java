package database;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shroudedhaven.MainActivity;
import com.example.shroudedhaven.ShroudedHavenApplications;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.entities.Trails;
import database.entities.User;
import database.typeConverters.LocalDateTypeConverter;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "User_database";
    public static final String USER_TABLE = "userTable";
    private static volatile UserDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static UserDatabase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    UserDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomDatabaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    public static void getDatabase(ShroudedHavenApplications shroudedHavenApplications) {
    }

    public abstract UserDAO userDAO();


}

