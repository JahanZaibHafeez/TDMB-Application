package com.jahanzaib.themoviedb.apicalls.usecase;

import com.jahanzaib.themoviedb.apicalls.BaseUseCase;
import com.jahanzaib.themoviedb.apicalls.BaseUseCaseCallback;
import com.jahanzaib.themoviedb.apicalls.entitymodel.MovieEntity;
import com.jahanzaib.themoviedb.apicalls.service.API;
import com.jahanzaib.themoviedb.apicalls.service.response.GetPopularMoviesResponse;


import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GetPopularMoviesUseCase extends BaseUseCase {

    public interface GetPopularMovieUseCaseCallback extends BaseUseCaseCallback {
        void onMoviesSearched(List<MovieEntity> movieEntities);
    }

    private String apiKey;
    private String query;

    public GetPopularMoviesUseCase(String apiKey, GetPopularMoviesUseCase.GetPopularMovieUseCaseCallback callback) {
        super(callback);
        this.apiKey = API.API_KEY;

    }

    @Override
    public void onRun() throws Throwable {
        API.http().popularMovies(apiKey, new Callback<GetPopularMoviesResponse>() {
            @Override
            public void success(GetPopularMoviesResponse popularMovieResponse, Response response) {
                ((GetPopularMovieUseCaseCallback) callback).onMoviesSearched(popularMovieResponse.getResults());
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
