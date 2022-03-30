package nl.avans.cinema.dataacces.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

import nl.avans.cinema.domain.Company;
import nl.avans.cinema.domain.Genre;

public class DateTypeConverter {

    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

    static Gson gson = new Gson();

    @TypeConverter
    public static Genre[] stringToGenreArray(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<Genre[]>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String genreArrayToString(Genre[] genres) {
        return gson.toJson(genres);
    }

    @TypeConverter
    public static Company[] stringToCompanyArray(String data){
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<Company[]>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String companyArrayToString(Company[] companies) {
        return gson.toJson(companies);
    }

}
