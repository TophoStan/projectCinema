package nl.avans.cinema.dataacces.api.task;

import nl.avans.cinema.dataacces.api.calls.MovieResponse;

public interface OnFetchData {
    void onRecievingMovie(MovieResponse movieResponse, String action);
}
