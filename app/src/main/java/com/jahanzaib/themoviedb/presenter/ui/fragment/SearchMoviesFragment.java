package com.jahanzaib.themoviedb.presenter.ui.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.jahanzaib.themoviedb.R;
import com.jahanzaib.themoviedb.presenter.mvp.model.MovieModel;
import com.jahanzaib.themoviedb.presenter.mvp.presenter.SearchMoviesPresenter;
import com.jahanzaib.themoviedb.presenter.mvp.view.SearchMoviesView;
import com.jahanzaib.themoviedb.presenter.ui.BaseFragment;
import com.jahanzaib.themoviedb.presenter.ui.activity.SearchMoviesActivity;

import java.util.ArrayList;
import java.util.List;


public class SearchMoviesFragment extends BaseFragment implements SearchMoviesView {

    public static final int QUERY_SUBMITTED = 1;
    private View mainView, loadingView;

    private Handler queryHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == QUERY_SUBMITTED) {
                presenter.performSearch(typedQuery);
            }
        }
    };

    private SearchMoviesPresenter presenter;

    private String typedQuery;

    public static SearchMoviesFragment newInstance() {
        return new SearchMoviesFragment();
    }

    @Override
    public void instantiatePresenter() {
        presenter = new SearchMoviesPresenter(this);
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
        return R.layout.fragment_search_movies;
    }

    @Override
    public void mapGUI(View view) {
        mainView = view.findViewById(R.id.results_container);
        loadingView = view.findViewById(R.id.view_loading);
    }

    @Override
    public void configureGUI() {

                    presenter.performSearch(SearchMoviesActivity.queryString);

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
        queryHandler.removeMessages(QUERY_SUBMITTED);
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
