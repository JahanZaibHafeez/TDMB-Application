package com.jahanzaib.themoviedb.apicalls.service.response;

import com.jahanzaib.themoviedb.apicalls.entitymodel.MovieEntity;

import java.util.List;

/**
 * Created by progr on 29/08/2017.
 */

public class GetPopularMoviesResponse {
    private List<MovieEntity> results;

    public List<MovieEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieEntity> results) {
        this.results = results;
    }
}
