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
}
