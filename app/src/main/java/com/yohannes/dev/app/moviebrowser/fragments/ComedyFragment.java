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

import com.yohannes.dev.app.moviebrowser.R;
import com.yohannes.dev.app.moviebrowser.adapters.MovieListAdapter;
import com.yohannes.dev.app.moviebrowser.adapters.OnLoadMoreListener;
import com.yohannes.dev.app.moviebrowser.data.Movie;
import com.yohannes.dev.app.moviebrowser.viewmodels.ComedyViewModel;

import java.util.List;

public class ComedyFragment extends Fragment {

    private ComedyViewModel comedyViewModel;
    private List<Movie> movieList;
    private MovieListAdapter movieListAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noResult;
    private Button retryButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("Message", "onCreateView: from ComedyFragment");
        comedyViewModel = new ViewModelProvider(this).get(ComedyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_comedy, container, false);

        recyclerView = root.findViewById(R.id.comdeyList);
        progressBar = root.findViewById(R.id.progresscircular);
        noResult = root.findViewById(R.id.comedyNoresult);
        retryButton = root.findViewById(R.id.tryAgainComedy);

        recyclerView.setHasFixedSize(true);
        movieListAdapter = new MovieListAdapter(this.getContext(), movieList, recyclerView);
        recyclerView.setAdapter(movieListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        movieListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                comedyViewModel.makeApiCall();
            }
        });

        showLoading();

        comedyViewModel.getMoviesListObserver().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null && movies.size() > 0){
                Log.e("Movie", String.valueOf(movies.size()));
                this.movieList = movies;
                movieListAdapter.setMovieList(movieList);
                showList();
            }else{
                showText();
            }
        });

        comedyViewModel.makeApiCall();

        retryButton.setOnClickListener(view -> comedyViewModel.makeApiCall());
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