package com.jahanzaib.themoviedb.presenter.mvp.presenter;


import com.jahanzaib.themoviedb.presenter.mvp.BasePresenter;
import com.jahanzaib.themoviedb.presenter.mvp.model.MovieModel;
import com.jahanzaib.themoviedb.presenter.mvp.view.MovieListView;

import java.util.List;



public class MovieListPresenter implements BasePresenter {

    private MovieListView view;
    private List<MovieModel> movies;

    public MovieListPresenter(MovieListView view, List<MovieModel> movies) {
        this.view = view;
        this.movies = movies;
    }

    @Override
    public void createView() {

        hideAllViews();


        if(movies.isEmpty()) {
            view.showEmpty("Loading...");
        } else {
            view.renderMovies(movies);
            view.showView();
        }
    }

    @Override
    public void destroyView() {}

    private void hideAllViews() {
        view.hideView();
        view.hideEmpty();
        view.hideRetry();
    }
}
