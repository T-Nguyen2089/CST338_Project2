package database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import database.entities.Trails;

@Dao
public interface TrailsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Trails trails);

    @Query("Select * from " + TrailsDatabase.TRAILS_TABLE)
    List<Trails> getAllRecords();
}
