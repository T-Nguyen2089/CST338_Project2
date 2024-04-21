package database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import database.DataBase;

@Entity(tableName = DataBase.USER_TABLE)
public class Hiker {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;

    public Hiker(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hiker hiker = (Hiker) o;
        return id == hiker.id && isAdmin == hiker.isAdmin && Objects.equals(username, hiker.username) && Objects.equals(password, hiker.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isAdmin);
    }
}
