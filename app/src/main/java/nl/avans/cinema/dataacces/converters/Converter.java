package nl.avans.cinema.dataacces.converters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.domain.Company;
import nl.avans.cinema.domain.Genre;

public class Converter {


    @TypeConverter
    public static List<Genre>fromString(String data) {

        Type listType = new TypeToken<List<Genre>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String fromList(List<Genre> list) {
        Gson gson = new Gson();

        return gson.toJson(list);
    }
    @TypeConverter
    public static List<Company>stringToCompanyList(String data) {

        Type listType = new TypeToken<List<Company>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String listToString(List<Company> list) {
        Gson gson = new Gson();

        return gson.toJson(list);
    }

    @TypeConverter
    public static String intListToString(List<Integer> genre_ids){
        Gson gson = new Gson();

        return gson.toJson(genre_ids);
    }
    @TypeConverter
    public static List<Integer>stringToIntList(String data) {

        Type listType = new TypeToken<List<Integer>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }
}
