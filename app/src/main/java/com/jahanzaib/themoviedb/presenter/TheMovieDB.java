package com.jahanzaib.themoviedb.presenter;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.jahanzaib.themoviedb.presenter.cachehandler.PicassoCache;
import com.jahanzaib.themoviedb.presenter.cachehandler.SharedPreferencesController;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;

public class TheMovieDB extends Application {
    public static Picasso PICASSO;
    public static JobManager JOB_MANAGER;
    public static SharedPreferencesController LOCAL_DATA;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        configureJobManager();
        configureDataLayer();
        configurePicasso();

    }

    private void configureJobManager() {
        JOB_MANAGER = new JobManager(this, new Configuration.Builder(this)
                .minConsumerCount(2)
                .maxConsumerCount(3)
                .loadFactor(3)
                .build());
    }

    private void configurePicasso() {
        PICASSO = PicassoCache.INSTANCE.getPicassoCache(this);
    }

    private void configureDataLayer() {
        LOCAL_DATA = new SharedPreferencesController(this);
    }
}
