package com.yohannes.dev.app.moviebrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.hannesdorfmann.swipeback.transformer.SlideSwipeBackTransformer;
import com.yohannes.dev.app.moviebrowser.data.Movie;
import com.yohannes.dev.app.moviebrowser.viewmodels.DetailViewModel;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {

    private DetailViewModel detailViewModel;
    private Movie detailMovie;
    private Movie movieDetail;

    private ConstraintLayout detailLayout;
    private ProgressBar progressBar;
    private TextView noResult;
    private ImageView backdropImage;
    private ImageView posterImage;
    private TextView titleText;
    private TextView detailDescription;
    private TextView rating;

    private MultiTransformation<Bitmap> multiTransformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.e("Message", "onCreate: from DetailActivity");
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        movieDetail = (Movie) getIntent().getSerializableExtra("Movie");

        detailLayout = findViewById(R.id.detailLayout);
        progressBar = findViewById(R.id.progressDetail);
        noResult = findViewById(R.id.detailNoresult);
        backdropImage = findViewById(R.id.backdropImage);
        posterImage = findViewById(R.id.posterImage);
        titleText = findViewById(R.id.titleText);
        detailDescription = findViewById(R.id.detailDescription);
        rating = findViewById(R.id.rating);

        multiTransformation = new MultiTransformation<>(new RoundedCornersTransformation(16, 0, RoundedCornersTransformation.CornerType.ALL));
        detailDescription.setMovementMethod(new ScrollingMovementMethod());
        showLoading();

        setupMovie(movieDetail);
        showDetail();

        ImageButton imageButton = findViewById(R.id.backButton);
        imageButton.setOnClickListener(view -> finish());
    }

    private void setupMovie(Movie movie) {
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getBackgroundPath())
                .timeout(60000)
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(backdropImage);

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .timeout(60000)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(posterImage);
        Log.e("Message", "backgroundpath " + "https://image.tmdb.org/t/p/w500" + movie.getBackgroundPath());
        Log.e("Message", "posterpath " + "https://image.tmdb.org/t/p/w500" + movie.getPosterPath());


        titleText.setText(movie.getTitle());
        detailDescription.setText(movie.getDescription());
        rating.setText(String.valueOf(movie.getRating()));
    }

    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
        detailLayout.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){
        progressBar.setVisibility(View.INVISIBLE);
        detailLayout.setVisibility(View.VISIBLE);
    }

    private void showText(){
        progressBar.setVisibility(View.INVISIBLE);
        detailLayout.setVisibility(View.INVISIBLE);
        noResult.setVisibility(View.VISIBLE);
    }

    private void showDetail(){
        progressBar.setVisibility(View.INVISIBLE);
        detailLayout.setVisibility(View.VISIBLE);
        noResult.setVisibility(View.INVISIBLE);
    }
}