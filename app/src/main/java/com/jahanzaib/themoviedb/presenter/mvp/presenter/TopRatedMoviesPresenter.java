package com.jahanzaib.themoviedb.presenter.mvp.presenter;


import com.jahanzaib.themoviedb.apicalls.entitymodel.ConfigurationEntity;
import com.jahanzaib.themoviedb.apicalls.entitymodel.MovieEntity;
import com.jahanzaib.themoviedb.apicalls.service.API;
import com.jahanzaib.themoviedb.apicalls.usecase.GetImageConfigurationUseCase;
import com.jahanzaib.themoviedb.apicalls.usecase.GetTopRatedMoviesUseCase;
import com.jahanzaib.themoviedb.presenter.TheMovieDB;
import com.jahanzaib.themoviedb.presenter.mapper.MovieMapper;
import com.jahanzaib.themoviedb.presenter.mvp.BasePresenter;
import com.jahanzaib.themoviedb.presenter.mvp.view.SearchMoviesView;

import java.util.List;

public class TopRatedMoviesPresenter implements BasePresenter {
    private SearchMoviesView view;
    private String apiKey;

    public TopRatedMoviesPresenter(SearchMoviesView view) {
        this.view =view;
        this.apiKey = API.API_KEY;
    }

    @Override
    public void createView() {
        hideAllViews();
        view.showLoading();

        checkIfHasTheBaseImageURL();
    }

    @Override
    public void destroyView() {
        view.cleanTimer();
    }

    public void performSearch() {
            view.hideView();
            view.showLoading();

            TheMovieDB.JOB_MANAGER.addJobInBackground(new GetTopRatedMoviesUseCase(apiKey, new GetTopRatedMoviesUseCase.GetTopRatedMoviesUseCaseCallback() {
                @Override
                public void onMoviesSearched(List<MovieEntity> movieEntities) {
                    view.hideLoading();
                    view.renderMoviesList(new MovieMapper().toModels(movieEntities));
                    view.showView();
                }

                @Override
                public void onError(String reason) {
                    view.hideLoading();
                    view.removeMoviesList();
                    view.showFeedback(reason);
                }
            }));

    }

    private void hideAllViews() {
        view.hideView();
        view.hideLoading();
    }

    private void checkIfHasTheBaseImageURL() {
        if(!TheMovieDB.LOCAL_DATA.hasBaseImageURL()) {
            TheMovieDB.JOB_MANAGER.addJobInBackground(new GetImageConfigurationUseCase(API.API_KEY, new GetImageConfigurationUseCase.GetImageConfigurationUseCaseCallback() {
                @Override
                public void onConfigurationDownloaded(ConfigurationEntity configurationEntity) {
                    TheMovieDB.LOCAL_DATA.storeBaseImageURL(configurationEntity.getBase_url());

                    showEmptyMovies();
                }

                @Override
                public void onError(String reason) {
                    view.hideLoading();
                    view.showView();
                    view.showFeedback(reason);
                }
            }));

        } else {
            showEmptyMovies();
        }
    }

    private void showEmptyMovies() {
        view.hideLoading();
        view.removeMoviesList();
        view.showView();
    }
}
