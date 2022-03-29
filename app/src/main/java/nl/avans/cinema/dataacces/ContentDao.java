package nl.avans.cinema.dataacces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import nl.avans.cinema.domain.Content;

@Dao
public interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertContent(Content content);
    @Query("")
    void deleteAll();
    @Query("")
    LiveData<List<Content>> getAllContent();
    @Query("")
    Content getSpecificContent(String contentName);
    @Delete
    void deleteWord(Content content);
    @Update
    void update(Content... contents);
}
