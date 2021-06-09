package com.yohannes.dev.app.moviebrowser.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yohannes.dev.app.moviebrowser.DetailActivity;
import com.yohannes.dev.app.moviebrowser.R;
import com.yohannes.dev.app.moviebrowser.data.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    Context context;
    List<Movie> movieList;

    public MovieListAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.title.setText(movieList.get(position).getTitle());
        //holder.description.setText(movieList.get(position).getDescription());
        //holder.date.setText(movieList.get(position).getDate());
        //holder.rating.setText(String.valueOf(movieList.get(position).getRating()));
        holder.singleMovie = movieList.get(position);

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + this.movieList.get(position).getBackgroundPath())
                .timeout(60000)
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(holder.movieImage);
        
        Log.e("URL", "https://image.tmdb.org/t/p/w500" + this.movieList.get(position).getBackgroundPath());
    }

    @Override
    public int getItemCount() {
        if (this.movieList != null){
            return this.movieList.size();
        }
        return 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView movieImage;
        TextView description;
        TextView date;
        TextView rating;

        Movie singleMovie;

        public MovieViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            movieImage = itemView.findViewById(R.id.movieImage);
            /*description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            rating = itemView.findViewById(R.id.rating);*/

            itemView.setOnClickListener(v -> {
                Intent detailView = new Intent(itemView.getContext(), DetailActivity.class);
                detailView.putExtra("Movie", singleMovie);
                Log.e("Message", "Movie id: " + singleMovie.getId());
                itemView.getContext().startActivity(detailView);
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
        }
    }
}
