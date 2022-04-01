package nl.avans.cinema.dataacces.api.task;

import nl.avans.cinema.dataacces.api.calls.MovieResults;

public interface OnFetchData {
    void onRecievingMovie(MovieResults movieResults, String action);
}
