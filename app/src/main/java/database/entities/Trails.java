package database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDateTime;
import java.util.Objects;

import database.TrailsDatabase;
import database.typeConverters.LocalDateTypeConverter;

@TypeConverters(LocalDateTypeConverter.class)
@Entity(tableName = TrailsDatabase.TRAILS_TABLE)
public class Trails {
    @PrimaryKey(autoGenerate = true)
    private int trailsId = 0;

    private String trailName = "";
    private double length = 0.0;
    private String description = "";
    private LocalDateTime date;

    public int getTrailsId() {
        return trailsId;
    }

    public void setTrailsId(int trailsId) {
        this.trailsId = trailsId;
    }

    public String getTrailName() {
        return trailName;
    }

    public void setTrailName(String trailName) {
        this.trailName = trailName;
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

    public Trails(String trailName, double length, String description) {
        this.trailName = trailName;
        this.length = length;
        this.description = description;
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        return  trailName + '\n' +
                "Length: " + length +'\n' +
                "Description: " + description + '\n' +
                "======================================\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trails trails = (Trails) o;
        return trailsId == trails.trailsId && Double.compare(trails.length, length) == 0 && Objects.equals(trailName, trails.trailName) && Objects.equals(description, trails.description) && Objects.equals(date, trails.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trailsId, trailName, length, description, date);
    }
}
