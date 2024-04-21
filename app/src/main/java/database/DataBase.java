package database;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shroudedhaven.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.entities.Hiker;

@Database(entities = {Hiker.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "database";
    public static final String USER_TABLE = "user_table";
    private static volatile DataBase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DataBase getDataBase(final Context context){
        if(INSTANCE == null){
            synchronized (DataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                                    DataBase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                HikerDAO dao = INSTANCE.hikerDAO();
                //dao.deleteAll();
                Hiker admin = new Hiker("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                Hiker testUser1 = new Hiker("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract HikerDAO hikerDAO();
}
