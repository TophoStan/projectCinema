package nl.avans.cinema.dataacces.api.calls;

public class RatingRequest {
    private double value;

    public RatingRequest(double value) {

        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
