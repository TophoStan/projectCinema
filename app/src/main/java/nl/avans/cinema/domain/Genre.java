package nl.avans.cinema.domain;

import java.io.Serializable;

public class Genre implements Serializable {
    private int genreId;
    private String name;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
