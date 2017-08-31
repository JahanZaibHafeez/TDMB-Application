package com.jahanzaib.themoviedb.presenter.mvp.view;

public interface LoadingImageView extends LoadDataView {
    void renderImage(String url);
}
