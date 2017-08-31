package com.jahanzaib.themoviedb.presenter.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.jahanzaib.themoviedb.R;
import com.jahanzaib.themoviedb.presenter.ui.BaseActivity;
import com.jahanzaib.themoviedb.presenter.ui.fragment.SearchMoviesFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class SearchMoviesActivity extends BaseActivity {
    private MaterialSearchView searchView;
    public static String queryString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.white));

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryString = query;
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(SearchMoviesFragment.newInstance(),"")
                        .commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queryString = newText;
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(SearchMoviesFragment.newInstance(),"")
                        .commit();
                //Do some magic
                return false;
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_default;
    }

    @Override
    public Fragment getMainFragment() {

        return SearchMoviesFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

}
