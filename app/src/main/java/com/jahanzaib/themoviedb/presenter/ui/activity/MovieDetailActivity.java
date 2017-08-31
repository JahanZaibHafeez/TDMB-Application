package com.jahanzaib.themoviedb.presenter.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jahanzaib.themoviedb.R;
import com.jahanzaib.themoviedb.presenter.mapper.MovieMapper;
import com.jahanzaib.themoviedb.presenter.mvp.model.MovieModel;
import com.jahanzaib.themoviedb.presenter.ui.BaseActivity;
import com.jahanzaib.themoviedb.presenter.ui.fragment.MovieDetailFragment;


public class MovieDetailActivity extends BaseActivity {

    public static final String TAG = MovieDetailActivity.class.getSimpleName();
    public static final String MOVIE_MODEL = TAG + ".MOVIE_MODEL";

    private MovieModel movieModel;

    public static Intent getCallingIntent(Context context, MovieModel movieModel) {
        Intent intentToBeCalled = new Intent(context, MovieDetailActivity.class);
        intentToBeCalled.putExtra(MOVIE_MODEL, new MovieMapper().serializeModel(movieModel));

        return intentToBeCalled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle args = getIntent().getExtras();

        if(args != null) {
            movieModel = new MovieMapper().deserializeModel(args.getString(MOVIE_MODEL));
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_with_toolbar;
    }

    @Override
    public Fragment getMainFragment() {
        return MovieDetailFragment.newInstance(movieModel);
    }
}
