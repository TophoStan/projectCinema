package nl.avans.cinema.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

public class MovieList {
    private List<Movie> list = new ArrayList<>();
    private int id;
    private String name;
    private int number_of_items;

    public MovieList(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Movie> getList() {
        return list;
    }

    public void addMovie(Movie movie) {
        list.add(movie);
    }

    public int getSize() {
        return list.size();
    }

    public int getTotal_results() {
        return number_of_items;
    }
}
