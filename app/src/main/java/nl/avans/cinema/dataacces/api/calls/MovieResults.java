package nl.avans.cinema.dataacces.api.calls;

import nl.avans.cinema.domain.Movie;

public class MovieResults {
    private int page;
    private Movie[] results;

    public Movie[] getMovies() {
        return results;
    }

    public int getPage() {
        return page;
    }
}
