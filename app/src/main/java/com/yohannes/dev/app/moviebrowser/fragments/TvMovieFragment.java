package com.yohannes.dev.app.moviebrowser.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.yohannes.dev.app.moviebrowser.R;
import com.yohannes.dev.app.moviebrowser.adapters.MovieListAdapter;
import com.yohannes.dev.app.moviebrowser.data.Movie;
import com.yohannes.dev.app.moviebrowser.viewmodels.ComedyViewModel;
import com.yohannes.dev.app.moviebrowser.viewmodels.TvMovieViewModel;

import java.util.List;

public class TvMovieFragment extends Fragment {

    private TvMovieViewModel tvMovieViewModel;
    private List<Movie> movieList;
    private MovieListAdapter movieListAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LottieAnimationView noResult;
    private Button retryButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("Message", "onCreateView: from TvMovieFragment");
        tvMovieViewModel = new ViewModelProvider(this).get(TvMovieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tv_movie, container, false);

        recyclerView = root.findViewById(R.id.tvMovieList);
        progressBar = root.findViewById(R.id.progressTvMovie);
        noResult = root.findViewById(R.id.tvMovieNoresult);
        retryButton = root.findViewById(R.id.tryAgainTvMovie);

        movieListAdapter = new MovieListAdapter(this.getContext(), movieList, recyclerView);
        recyclerView.setAdapter(movieListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        showLoading();

        tvMovieViewModel.getMoviesListObserver().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null && movies.size() > 0){
                Log.e("Movie", String.valueOf(movies.size()));
                this.movieList = movies;
                movieListAdapter.setMovieList(movieList);
                showList();
            }else{
                showText();
            }
        });

        tvMovieViewModel.makeApiCall();

        retryButton.setOnClickListener(view -> tvMovieViewModel.makeApiCall());
        return root;
    }

    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void hideList(){
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showText(){
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        noResult.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.VISIBLE);
    }

    private void showList(){
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        noResult.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.INVISIBLE);
    }
}