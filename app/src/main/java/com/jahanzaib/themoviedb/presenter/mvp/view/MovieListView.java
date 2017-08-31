package com.jahanzaib.themoviedb.presenter.mvp.view;

import com.jahanzaib.themoviedb.presenter.mvp.model.MovieModel;

import java.util.List;



public interface MovieListView extends LoadDataView {

    void renderMovies(List<MovieModel> movies);
    void clearMovies();

}
