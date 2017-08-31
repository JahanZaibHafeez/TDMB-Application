package com.jahanzaib.themoviedb.apicalls.usecase;


import com.jahanzaib.themoviedb.apicalls.BaseUseCase;
import com.jahanzaib.themoviedb.apicalls.BaseUseCaseCallback;
import com.jahanzaib.themoviedb.apicalls.entitymodel.MovieEntity;
import com.jahanzaib.themoviedb.apicalls.service.API;
import com.jahanzaib.themoviedb.apicalls.service.response.SearchMovieResponse;

import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchMovieUseCase extends BaseUseCase {

    public interface SearchMovieUseCaseCallback extends BaseUseCaseCallback {
        void onMoviesSearched(List<MovieEntity> movieEntities);
    }

    private String apiKey;
    private String query;

    public SearchMovieUseCase(String apiKey, String query, SearchMovieUseCaseCallback callback) {
        super(callback);
        this.apiKey = API.API_KEY;
        this.query = query;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().search(apiKey, query, new Callback<SearchMovieResponse>() {
            @Override
            public void success(SearchMovieResponse searchMovieResponse, Response response) {
                ((SearchMovieUseCaseCallback) callback).onMoviesSearched(searchMovieResponse.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getKind() == RetrofitError.Kind.NETWORK) {
                    errorReason = NETWORK_ERROR;
                } else {
                    errorReason = error.getResponse().getReason();
                }
                onCancel();
            }
        });
    }
}
