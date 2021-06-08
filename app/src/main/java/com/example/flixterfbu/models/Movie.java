package com.example.flixterfbu.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    String adult;
    String backdrop_path;
    String id;
    String original_language;
    String original_title;
    String overview;
    String poster_path;
    String release_date;
    String title;
    Double vote_average;

    // no-arg, empty constructor required for Parceler
    public Movie() {}

    public Movie (JSONObject jsonObject) throws JSONException {
        adult               = jsonObject.getString("adult");
        backdrop_path       = jsonObject.getString("backdrop_path");
        id                  = jsonObject.getString("id");
        original_language   = jsonObject.getString("original_language");
        original_title      = jsonObject.getString("original_title");
        overview            = jsonObject.getString("overview");
        poster_path         = jsonObject.getString("poster_path");
        release_date        = jsonObject.getString("release_date");
        title               = jsonObject.getString("title");
        vote_average        = jsonObject.getDouble("vote_average");
    }

    public static List<Movie> fromJsonArray (JSONArray jsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            // Adds a new movie object to each index from the json array to the array list
            movies.add(new Movie( jsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdrop_path);
    }

    public String getId() {
        return id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", poster_path);
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public Double getVote_average() {
        return vote_average;
    }

}
