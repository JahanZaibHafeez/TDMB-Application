package com.jahanzaib.themoviedb.presenter.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jahanzaib.themoviedb.R;
import com.jahanzaib.themoviedb.presenter.Utility;


public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private View container;

    private boolean shouldFade = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        container = findViewById(R.id.container);

        configureToolbar();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, getMainFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!shouldFade) {
            Utility.restoreToolbarColor(this, toolbar);
        }
    }

    private void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public abstract int getLayoutResource();
    public abstract Fragment getMainFragment();

    public Toolbar getToolbar() {
        return toolbar;
    }
    public void setShouldFadeToolbar(boolean shouldFade) {
        this.shouldFade = shouldFade;
    }
    public void removeContentPaddingTop() {
        if(container != null) {
            container.setPadding(0,0,0,0);
        }
    }
}
