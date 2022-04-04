package nl.avans.cinema.dataacces.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.User;

@Dao
public interface CinemaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Query("SELECT * FROM movies ORDER BY title ASC")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE id = :id")
    Movie getMovieById(int id);

    @Query("DELETE FROM movies")
    void deleteAll();

    @Query("SELECT * FROM User")
    User getUser();

    @Query("DELETE FROM User")
    void deleteUsers();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User user);



}
