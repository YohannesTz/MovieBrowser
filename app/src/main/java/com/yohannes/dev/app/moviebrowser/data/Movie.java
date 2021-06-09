package com.yohannes.dev.app.moviebrowser.data;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    private int id;

    private String title;

    @SerializedName("overview")
    private String description;

    @SerializedName("backdrop_path")
    private String backgroundPath;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String date;

    @SerializedName("vote_average")
    private float rating;

    public Movie(int id, String title, String description, String backgroundPath, String date, int rating, String posterPath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.backgroundPath = backgroundPath;
        this.date = date;
        this.rating = rating;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public String getDate() {
        return date;
    }

    public float getRating() {
        return rating;
    }

    public String getPosterPath() {
        return posterPath;
    }

    @NonNull
    @Override
    public String toString() {
        return title + ", " + description;
    }
}
