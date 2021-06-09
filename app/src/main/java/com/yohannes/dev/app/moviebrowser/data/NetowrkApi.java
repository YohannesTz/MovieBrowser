package com.yohannes.dev.app.moviebrowser.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetowrkApi {

    //get movie by genre
    @GET("discover/movie")
    Call<List<Movie>> getMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("with_genres") int genereId);

    //get movie detial by id
    @GET("/movie/{movie_id}")
    Call<Movie> getMovieDetail(@Query("api_key") String apiKey, @Path("movie_id") int movieId);

    @GET("discover/movie")
    Call<MainResponse> getResponse(@Query("api_key") String apiKey, @Query("language") String language, @Query("with_genres") int genereId);


}
