package nl.avans.cinema;

import org.junit.Test;

import static org.junit.Assert.*;

import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.ui.DetailActivity;
import nl.avans.cinema.ui.MainActivity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void calculateRatingRequires10Ensures5(){
        assertEquals(5, DetailActivity.calculateRating(10), 0.1);
    }
    @Test
    public void calculateRatingRequires5Ensures2dot5(){
        assertEquals(2.5, DetailActivity.calculateRating(5), 0.1);
    }
    @Test
    public void calculateRatingRequires0Ensures0(){
        assertEquals(0, DetailActivity.calculateRating(0), 0.1);
    }
}