package nl.avans.cinema.dataacces.api.calls;

import java.util.List;

import nl.avans.cinema.domain.Video;

public class VideoResults {
    private int id;
    private Video[] results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

    }

    public Video[] getResults() {
        return results;
    }

    public void setResults(Video[] results) {
        this.results = results;
    }
}
