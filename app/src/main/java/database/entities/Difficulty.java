package database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import database.DifficultyDatabase;

@Entity(tableName = DifficultyDatabase.DIFFICULTY_TABLE)
public class Difficulty {
    @PrimaryKey(autoGenerate = true)
    private int difficultyId = 0;
    private String rating = "";
    private String description = "";

    public int getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(int difficultyId) {
        this.difficultyId = difficultyId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Difficulty that = (Difficulty) o;
        return difficultyId == that.difficultyId && Objects.equals(rating, that.rating) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(difficultyId, rating, description);
    }

    @Override
    public String toString() {
        return "Difficulty{" +
                "rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
