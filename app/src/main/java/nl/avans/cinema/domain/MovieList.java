package nl.avans.cinema.domain;

import java.util.ArrayList;

import javax.xml.namespace.QName;

public class MovieList {
    private ArrayList<Movie> list = new ArrayList<>();
    private int id;
    private String name;

    public MovieList(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Movie> getList() {
        return list;
    }

    public void addMovie(Movie movie) {
        list.add(movie);
    }

    public int getSize() {
        return list.size();
    }

}
