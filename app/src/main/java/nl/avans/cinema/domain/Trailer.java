package nl.avans.cinema.domain;

import androidx.room.ColumnInfo;
import androidx.room.RenameColumn;

public class Trailer {
    private int trailerId;
    private int duration;
    @ColumnInfo(name = "trailerTitle")
    private String title;
    private Review[] reviews;

    public Review[] getReviews() {
        return reviews;
    }

    public void setReviews(Review[] reviews) {
        this.reviews = reviews;
    }

    public int getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(int trailerId) {
        this.trailerId = trailerId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
