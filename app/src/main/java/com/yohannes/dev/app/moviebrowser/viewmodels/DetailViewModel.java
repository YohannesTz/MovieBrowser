package com.yohannes.dev.app.moviebrowser.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yohannes.dev.app.moviebrowser.R;
import com.yohannes.dev.app.moviebrowser.data.Movie;
import com.yohannes.dev.app.moviebrowser.data.NetowrkApi;
import com.yohannes.dev.app.moviebrowser.data.RetroInstance;
import com.yohannes.dev.app.moviebrowser.data.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends AndroidViewModel {

    private MutableLiveData<Movie> movieMutableLiveData;
    private MutableLiveData<Integer> idLiveData;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        this.movieMutableLiveData = movieMutableLiveData;
    }

    public MutableLiveData<Movie> getMovieObserver(){
        return movieMutableLiveData;
    }

    public void setMovieId(Integer id){
        idLiveData.postValue(id);
    }

    public void makeApiCall(){
        NetowrkApi netowrkApi = RetroInstance.getRetrofitClient().create(NetowrkApi.class);
        Call<Movie> movieCall = netowrkApi.getMovieDetail(getApplication().getString(R.string.api_key), idLiveData.getValue());

        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.e("RESPONSE", response.body().toString());
                movieMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("RESPONSE", "on Failure: ",t);
                movieMutableLiveData.postValue(null);
            }
        });
    }


}
