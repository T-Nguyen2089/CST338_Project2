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

import database.entities.Difficulty;

import database.typeConverters.LocalDateTypeConverter;

@Database(entities = {Difficulty.class}, version = 1, exportSchema = false)
public abstract class DifficultyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Difficulty_database";
    public static final String DIFFICULTY_TABLE = "difficultyTable";
    private static volatile database.DifficultyDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static database.DifficultyDatabase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (database.DifficultyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    DifficultyDatabase.class, DATABASE_NAME)
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
            });
        }
    };

    public static void getDatabase(ShroudedHavenApplications shroudedHavenApplications) {
    }
}

