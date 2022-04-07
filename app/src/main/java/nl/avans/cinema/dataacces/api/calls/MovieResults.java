package nl.avans.cinema.dataacces.api.calls;

import nl.avans.cinema.domain.Movie;

public class MovieResults {
    private int page;
    private Movie[] results;
    private int total_pages;

    public int getTotal_pages() {
        return total_pages;
    }

    public Movie[] getMovies() {
        return results;
    }

    public int getPage() {
        return page;
    }
}
