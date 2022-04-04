package nl.avans.cinema.domain;

import androidx.room.Ignore;

import java.io.Serializable;

public class Company implements Serializable {
    private int companyId;
    private String name;
    private String logo_path;
    private String origin_country;
    @Ignore
    private FilmCrew filmCrew;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }

    public FilmCrew getFilmCrew() {
        return filmCrew;
    }

    public void setFilmCrew(FilmCrew filmCrew) {
        this.filmCrew = filmCrew;
    }
}
