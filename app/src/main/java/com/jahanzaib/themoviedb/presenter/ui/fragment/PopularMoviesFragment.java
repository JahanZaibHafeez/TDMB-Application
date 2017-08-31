package com.jahanzaib.themoviedb.presenter.ui.fragment;


import android.view.View;
import android.widget.Toast;

import com.jahanzaib.themoviedb.R;
import com.jahanzaib.themoviedb.presenter.mvp.model.MovieModel;
import com.jahanzaib.themoviedb.presenter.mvp.presenter.PopularMoviesPresenter;
import com.jahanzaib.themoviedb.presenter.mvp.view.SearchMoviesView;
import com.jahanzaib.themoviedb.presenter.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class PopularMoviesFragment extends BaseFragment implements SearchMoviesView {

    private View mainView, loadingView;

    private PopularMoviesPresenter presenter;

    public static PopularMoviesFragment newInstance() {
        return new PopularMoviesFragment();
    }

    @Override
    public void instantiatePresenter() {
        presenter = new PopularMoviesPresenter(this);
    }

    @Override
    public void initializePresenter() {
        presenter.createView();
    }

    @Override
    public void finalizePresenter() {
        presenter.destroyView();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_popular_videos;
    }

    @Override
    public void mapGUI(View view) {

        mainView = view.findViewById(R.id.results_container);
        loadingView = view.findViewById(R.id.view_loading);
    }

    @Override
    public void configureGUI() {


        presenter.performSearch();

    }

    @Override
    public void showView() {
        mainView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        mainView.setVisibility(View.GONE);
    }

    @Override
    public void showFeedback(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void destroyItself() {
        getActivity().finish();
    }

    @Override
    public void renderMoviesList(List<MovieModel> movies) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.results_container, MovieListFragment.newInstance(movies))
                .commit();
    }

    @Override
    public void removeMoviesList() {
        renderMoviesList(new ArrayList<MovieModel>());
    }

    @Override
    public void cleanTimer() {

    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showRetry(String msg) {}

    @Override
    public void hideRetry() {}

    @Override
    public void showEmpty(String msg) {}

    @Override
    public void hideEmpty() {}
}

