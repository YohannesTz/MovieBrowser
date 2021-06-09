package com.yohannes.dev.app.moviebrowser.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainResponse {

    private int page;
    private ArrayList<Movie> results;
    @SerializedName("total_page")
    private int totalPage;
    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
