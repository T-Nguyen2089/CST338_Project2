
package database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shroudedhaven.AdminActivity;
import com.example.shroudedhaven.MainActivity;
import com.example.shroudedhaven.TrailsActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.entities.Trails;
import database.typeConverters.LocalDateTypeConverter;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {Trails.class}, version = 1, exportSchema = false)
public abstract class TrailsDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Trails_database";
    public static final String TRAILS_TABLE = "trailsTable";
    private static volatile TrailsDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TrailsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TrailsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TrailsDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(AdminActivity.TAG, "DATABASE CREATED!");

        }
    };

    public abstract TrailsDAO trailsDAO();
}
