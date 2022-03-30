package nl.avans.cinema.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.List;

import nl.avans.cinema.dataacces.converters.DateTypeConverter;
import nl.avans.cinema.dataacces.converters.GithubTypeConverter;

@Entity(tableName = "movies")
@TypeConverters({DateTypeConverter.class, GithubTypeConverter.class})
public class Movie {

    @PrimaryKey
    private int id;
    private String title;
    private String alternativeTitle;
    private int rating;
    private Date releaseDate;
    private int duration;
    private int imageId;
    private String poster_path;
    private int amountOfSeasons;
    @ColumnInfo(name = "genreList")
    private List<Genre> genres;
    private List<Company> companies;

    private boolean adult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlternativeTitle() {
        return alternativeTitle;
    }

    public void setAlternativeTitle(String alternativeTitle) {
        this.alternativeTitle = alternativeTitle;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getAmountOfSeasons() {
        return amountOfSeasons;
    }

    public void setAmountOfSeasons(int amountOfSeasons) {
        this.amountOfSeasons = amountOfSeasons;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
