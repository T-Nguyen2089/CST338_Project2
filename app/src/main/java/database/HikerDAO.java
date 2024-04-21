package database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import database.DataBase;
import database.entities.Hiker;

@Dao
public interface HikerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Hiker... hiker);

    @Delete
    void delete(Hiker hiker);

    @Query("SELECT * FROM " + DataBase.USER_TABLE + " ORDER BY username")
    LiveData<List<Hiker>> getAllHikers();

    @Query("DELETE from " + DataBase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * from " + DataBase.USER_TABLE + " WHERE username == :hikername")
    LiveData<Hiker> getHikerByHikerName(String hikername);
}
