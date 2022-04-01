package nl.avans.cinema.dataacces.api.calls;

import java.util.List;

import nl.avans.cinema.domain.Cast;
import nl.avans.cinema.domain.Crew;

public class CreditResults {
    private int id;
    private List<Cast> cast;
    private List<Crew> crew;

    public int getId() {
        return id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }
}
