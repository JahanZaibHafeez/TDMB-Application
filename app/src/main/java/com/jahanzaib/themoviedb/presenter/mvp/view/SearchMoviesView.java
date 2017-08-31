package com.jahanzaib.themoviedb.presenter.mvp.view;

import com.jahanzaib.themoviedb.presenter.mvp.model.MovieModel;

import java.util.List;



public interface SearchMoviesView extends LoadDataView {

    void renderMoviesList(List<MovieModel> movies);
    void removeMoviesList();

    void cleanTimer();
}
