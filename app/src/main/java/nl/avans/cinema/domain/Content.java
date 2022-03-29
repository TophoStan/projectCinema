package nl.avans.cinema.domain;

import java.util.Date;

public abstract class Content {
    private int contentId;
    private String title;
    private String alternativeTitle;
    private int rating;
    private Date releaseDate;
    private int duration;
    private int imageId;
    private Genre genre;
    private Company company;
    private Trailer trailer;
}
