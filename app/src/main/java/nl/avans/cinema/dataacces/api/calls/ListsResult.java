package nl.avans.cinema.dataacces.api.calls;

import java.util.LinkedList;
import java.util.List;

import nl.avans.cinema.domain.MovieList;

public class ListsResult {
    private List<MovieList> results;

    public List<MovieList> getResults() {
        return results;
    }
}
