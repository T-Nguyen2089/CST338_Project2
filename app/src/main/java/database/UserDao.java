package database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import database.entities.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + DataBase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllHikers();

    @Query("DELETE from " + DataBase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * from " + DataBase.USER_TABLE + " WHERE username == :username")

    LiveData<User> getUserByUsername(String username);
}
