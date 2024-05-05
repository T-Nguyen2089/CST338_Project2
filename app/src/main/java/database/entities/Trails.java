package database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import database.TrailsDatabase;

@Entity(tableName = TrailsDatabase.TRAILS_TABLE)
public class Trails {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name = "";
    private double length = 0.0;
    private String description = "";
    private LocalDateTime date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Trails(String name, double length, String description) {
        this.name = name;
        this.length = length;
        this.description = description;
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        return  name + '\n' +
                "Length: " + length +'\n' +
                "Description: " + description + '\n' +
                "Date:" + date.toString() + '\n' +
                "======================================\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trails trails = (Trails) o;
        return id == trails.id && Double.compare(trails.length, length) == 0 && Objects.equals(name, trails.name) && Objects.equals(description, trails.description) && Objects.equals(date, trails.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, length, description, date);
    }
}
