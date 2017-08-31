package com.jahanzaib.themoviedb.presenter.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jahanzaib.themoviedb.R;
import com.jahanzaib.themoviedb.database.handler.DatabaseHandler;
import com.jahanzaib.themoviedb.database.model.Movie;
import com.jahanzaib.themoviedb.presenter.TheMovieDB;
import com.jahanzaib.themoviedb.presenter.mvp.model.MovieModel;
import com.jahanzaib.themoviedb.presenter.ui.activity.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private List<MovieModel> data = new ArrayList<>();
    private byte[] imageData;
    DatabaseHandler db;
    public Movie movie;



    public MoviesAdapter(Context context) {
        this.context = context;
        db = new DatabaseHandler(context);
    }

    public void setData(List<MovieModel> movies) {
        data = movies;
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vHolder, int i) {
        final MovieModel movieModel = data.get(i);

         vHolder.title.setText(movieModel.getName());


        if (TextUtils.isEmpty(movieModel.getYearOfRelease())) {
             vHolder.subtitle.setVisibility(View.GONE);
        } else {
             vHolder.subtitle.setText(movieModel.getYearOfRelease());
        }

        TheMovieDB.PICASSO.load(movieModel.getSmallCover()).into(vHolder.cover);


         vHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie = new Movie();
                movie.setMovieId(String.valueOf(movieModel.getId()));
                movie.setName(movieModel.getName());
                movie.setReleaseYear(movieModel.getYearOfRelease());
                movie.setImageData(movieModel.getSmallCover());
                showPopupMenu(vHolder.overflow);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        private ImageView cover, overflow;
        private TextView title, subtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            cover = (ImageView) itemView.findViewById(R.id.image);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(MovieDetailActivity.getCallingIntent(context, data.get(getPosition())));
                }
            }, 200);
        }


    }
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.movie_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.save_to_device:
                    if (!db.checkAvailabilty(movie.getMovieId())) {
                        db.addMovie(movie);
                        Toast.makeText(context,"Successfully Saved",Toast.LENGTH_LONG).show();
                    }else
                    {
                        Toast.makeText(context,"Already Exist in Database",Toast.LENGTH_LONG).show();
                    }
                    List<Movie> movies = db.getAllMovies();

                    for (Movie mov : movies) {
                        String log = "Id: " + mov.getMovieId() + " , Name: " + mov.getName() + " , Image: " + mov.getImageData();
                        // Writing Contacts to log
                        Log.d("Name: ", log);
                    }
                    return true;
                default:
            }
            return false;
        }
    }


}
