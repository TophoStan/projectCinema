package nl.avans.cinema.dataacces.api.calls;

import java.util.List;

import nl.avans.cinema.domain.Genre;

public class GenreListResult {
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
