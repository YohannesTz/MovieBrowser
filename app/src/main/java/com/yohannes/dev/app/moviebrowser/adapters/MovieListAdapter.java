package com.yohannes.dev.app.moviebrowser.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yohannes.dev.app.moviebrowser.DetailActivity;
import com.yohannes.dev.app.moviebrowser.R;
import com.yohannes.dev.app.moviebrowser.data.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    Context context;
    List<Movie> movieList;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public MovieListAdapter(Context context, List<Movie> movieList, RecyclerView recyclerView) {
        this.context = context;
        this.movieList = movieList;

        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                    if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyItemInserted(movieList.size());
        setLoaded();
    }

    @Override
    public int getItemViewType(int position) {
        return movieList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
            vh = new MovieViewHolder(view);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            vh = new ProgressViewHolder(v);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MovieViewHolder) {
            ((MovieViewHolder) holder).title.setText(movieList.get(position).getTitle());
            ((MovieViewHolder) holder).singleMovie = movieList.get(position);

            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500" + this.movieList.get(position).getPosterPath())
                    .timeout(60000)
                    .apply(RequestOptions.centerCropTransform())
                    .placeholder(R.drawable.ic_image_mountain)
                    .error(R.drawable.ic_image_mountain)
                    .into(((MovieViewHolder) holder).movieImage);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        if (this.movieList != null){
            return this.movieList.size();
        }
        return 0;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
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

            itemView.setOnClickListener(v -> {
                Intent detailView = new Intent(itemView.getContext(), DetailActivity.class);
                detailView.putExtra("Movie", singleMovie);
                Log.e("Message", "Movie id: " + singleMovie.getId());
                itemView.getContext().startActivity(detailView);
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
        }
    }

    private class ProgressViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        private ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar);
        }
    }
}
