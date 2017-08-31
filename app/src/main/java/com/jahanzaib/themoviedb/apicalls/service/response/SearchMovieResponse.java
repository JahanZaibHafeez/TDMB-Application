package com.jahanzaib.themoviedb.apicalls.service.response;

import com.jahanzaib.themoviedb.apicalls.entitymodel.MovieEntity;

import java.util.List;


public class SearchMovieResponse {
    private List<MovieEntity> results;

    public List<MovieEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieEntity> results) {
        this.results = results;
    }
}
