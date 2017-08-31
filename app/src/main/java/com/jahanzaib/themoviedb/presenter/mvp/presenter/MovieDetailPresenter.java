package com.jahanzaib.themoviedb.presenter.mvp.presenter;

import android.text.TextUtils;

import com.jahanzaib.themoviedb.apicalls.entitymodel.MovieDetailEntity;
import com.jahanzaib.themoviedb.apicalls.service.API;
import com.jahanzaib.themoviedb.apicalls.usecase.GetMovieDetailUseCase;
import com.jahanzaib.themoviedb.presenter.TheMovieDB;
import com.jahanzaib.themoviedb.presenter.mapper.MovieMapper;
import com.jahanzaib.themoviedb.presenter.mvp.BasePresenter;
import com.jahanzaib.themoviedb.presenter.mvp.model.MovieModel;
import com.jahanzaib.themoviedb.presenter.mvp.view.MovieDetailView;


public class MovieDetailPresenter implements BasePresenter {

    private MovieDetailView view;
    private MovieModel movieModel;
    private String apiKey;

    public MovieDetailPresenter(MovieDetailView view, MovieModel movieModel) {
        this.view = view;
        this.movieModel = movieModel;
        this.apiKey = API.API_KEY;
    }

    @Override
    public void createView() {
        hideAllViews();
        view.showLoading();

        downloadMovieDetails();
    }

    @Override
    public void destroyView() {}

    public void onHomepageClicked() {
        if(!TextUtils.isEmpty(movieModel.getHomepage())) {
            view.openMovieWebsite(movieModel.getHomepage());
        }
    }

    public void onGalleryClicked() {
        view.openGallery();
    }

    public void onMainViewScrolled() {
        view.updateToolbarColor();
    }

    private void hideAllViews() {
        view.hideView();
        view.hideLoading();
        view.hideRetry();
        view.hideEmpty();
    }

    private void downloadMovieDetails() {
        TheMovieDB.JOB_MANAGER.addJobInBackground(new GetMovieDetailUseCase(apiKey, movieModel.getId(), new GetMovieDetailUseCase.GetMovieDetailUseCaseCallback() {
            @Override
            public void onMovieDetailLoaded(MovieDetailEntity movieDetailEntity) {
                updateMovieModel(movieDetailEntity);

                view.updateBackground(movieModel.getBigCover());
                view.updateTitle(movieModel.getName());

                if (TextUtils.isEmpty(movieModel.getYearOfRelease())) {
                    view.hideYearOfRelease();
                } else {
                    view.updateYearOfRelease(movieModel.getYearOfRelease());
                }

                if (TextUtils.isEmpty(movieModel.getHomepage())) {
                    view.hideHomepage();
                } else {
                    view.updateHomepage(movieModel.getHomepage());
                }

                if (movieModel.getCompanies().isEmpty()) {
                    view.hideCompanies();
                } else {
                    view.updateCompanies(movieModel.getCompanies());
                }

                if (TextUtils.isEmpty(movieModel.getTagline())) {
                    view.hideTagline();
                } else {
                    view.updateTagline(movieModel.getTagline());
                }

                if (TextUtils.isEmpty(movieModel.getOverview())) {
                    view.hideOverview();
                } else {
                    view.updateOverview(movieModel.getOverview());
                }

                view.hideLoading();
                view.showView();
            }

            @Override
            public void onError(String reason) {
                view.showFeedback(reason);
                view.destroyItself();
            }
        }));
    }

    private void updateMovieModel(MovieDetailEntity detailEntity) {
        movieModel = new MovieMapper().addDetails(movieModel, detailEntity);
    }
}
