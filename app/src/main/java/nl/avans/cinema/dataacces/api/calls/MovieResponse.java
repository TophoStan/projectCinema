package nl.avans.cinema.dataacces.api.calls;

import nl.avans.cinema.domain.Movie;

public class MovieResponse {
    private int page;
    private Movie[] results;

    public Movie[] getMovies() {
        return results;
    }

    public int getPage() {
        return page;
    }
}
