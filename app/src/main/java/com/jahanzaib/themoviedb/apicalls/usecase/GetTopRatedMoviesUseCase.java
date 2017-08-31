package com.jahanzaib.themoviedb.apicalls.usecase;

import com.jahanzaib.themoviedb.apicalls.BaseUseCase;
import com.jahanzaib.themoviedb.apicalls.BaseUseCaseCallback;
import com.jahanzaib.themoviedb.apicalls.entitymodel.MovieEntity;
import com.jahanzaib.themoviedb.apicalls.service.API;
import com.jahanzaib.themoviedb.apicalls.service.response.GetTopRatedMoviesResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by progr on 29/08/2017.
 */

public class GetTopRatedMoviesUseCase extends BaseUseCase {

    public interface GetTopRatedMoviesUseCaseCallback extends BaseUseCaseCallback {
        void onMoviesSearched(List<MovieEntity> movieEntities);
    }

    private String apiKey;


    public GetTopRatedMoviesUseCase(String apiKey, GetTopRatedMoviesUseCase.GetTopRatedMoviesUseCaseCallback callback) {
        super(callback);
        this.apiKey = API.API_KEY;

    }

    @Override
    public void onRun() throws Throwable {
        API.http().topRatedMovies(apiKey, new Callback<GetTopRatedMoviesResponse>() {
            @Override
            public void success(GetTopRatedMoviesResponse topRatedMovieResponse, Response response) {
                ((GetTopRatedMoviesUseCase.GetTopRatedMoviesUseCaseCallback) callback).onMoviesSearched(topRatedMovieResponse.getResults());
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
