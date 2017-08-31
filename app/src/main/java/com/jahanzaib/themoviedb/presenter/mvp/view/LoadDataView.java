package com.jahanzaib.themoviedb.presenter.mvp.view;


import com.jahanzaib.themoviedb.presenter.mvp.BaseView;

public interface LoadDataView extends BaseView {

    void showLoading();
    void hideLoading();

    void showRetry(String msg);
    void hideRetry();

    void showEmpty(String msg);
    void hideEmpty();
}
