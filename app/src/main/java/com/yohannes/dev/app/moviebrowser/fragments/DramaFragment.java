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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yohannes.dev.app.moviebrowser.R;
import com.yohannes.dev.app.moviebrowser.adapters.MovieListAdapter;
import com.yohannes.dev.app.moviebrowser.data.Movie;
import com.yohannes.dev.app.moviebrowser.viewmodels.DramaViewModel;

import java.util.List;

public class DramaFragment extends Fragment {

    private DramaViewModel dramaViewModel;
    private List<Movie> movieList;
    private MovieListAdapter movieListAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("Message", "onCreateView: from DramaFragment");
        dramaViewModel = new ViewModelProvider(this).get(DramaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_drama, container, false);

        recyclerView = root.findViewById(R.id.dramaList);
        progressBar = root.findViewById(R.id.progressDrama);
        noResult = root.findViewById(R.id.dramaNoresult);

        movieListAdapter = new MovieListAdapter(this.getContext(), movieList);
        recyclerView.setAdapter(movieListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        showLoading();

        dramaViewModel.getMoviesListObserver().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null && movies.size() > 0){
                Log.e("Movie", String.valueOf(movies.size()));
                this.movieList = movies;
                movieListAdapter.setMovieList(movieList);
                showList();
            }else{
                showText();
            }
        });

        dramaViewModel.makeApiCall();
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
    }

    private void showList(){
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        noResult.setVisibility(View.INVISIBLE);
    }
}