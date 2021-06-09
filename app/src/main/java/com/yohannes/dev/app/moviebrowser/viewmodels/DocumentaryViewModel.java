package com.yohannes.dev.app.moviebrowser.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yohannes.dev.app.moviebrowser.data.MainResponse;
import com.yohannes.dev.app.moviebrowser.data.Movie;
import com.yohannes.dev.app.moviebrowser.data.NetowrkApi;
import com.yohannes.dev.app.moviebrowser.data.RetroInstance;
import com.yohannes.dev.app.moviebrowser.data.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentaryViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movieList;

    public DocumentaryViewModel(){
        movieList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getMoviesListObserver(){
        return movieList;
    }

    public void makeApiCall(){
        NetowrkApi netowrkApi = RetroInstance.getRetrofitClient().create(NetowrkApi.class);
        Call<MainResponse> mainResponseCall = netowrkApi.getResponse(Utils.API_KEY, Utils.LANGUAGE, 99);

        mainResponseCall.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                Log.e("RESPONSE", response.body().toString());
                movieList.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Log.e("RESPONSE", "on Failure: ",t);
                movieList.postValue(null);
            }
        });
    }
}
