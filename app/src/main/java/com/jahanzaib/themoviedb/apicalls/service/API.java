package com.jahanzaib.themoviedb.apicalls.service;


import com.jahanzaib.themoviedb.apicalls.entitymodel.MovieDetailEntity;
import com.jahanzaib.themoviedb.apicalls.service.response.GetImageConfigurationResponse;
import com.jahanzaib.themoviedb.apicalls.service.response.GetPopularMoviesResponse;
import com.jahanzaib.themoviedb.apicalls.service.response.GetTopRatedMoviesResponse;
import com.jahanzaib.themoviedb.apicalls.service.response.SearchMovieResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public class API {

    public static final String BASE_URL = "http://api.themoviedb.org/3";
    public static final String API_KEY = "4e6bb280d2a39b2618be984436986734";

    public interface ROUTES {

        //CONFIGURATIONS
        @GET("/configuration")
        void configurations(@Query("api_key") String apiKey, Callback<GetImageConfigurationResponse> callback);

        //MOVIE SEARCH AUTOCOMPLETE
        @GET("/search/movie")
        void search(@Query("api_key") String apiKey, @Query("query") String query, Callback<SearchMovieResponse> callback);

        //MOVIE DETAIL
        @GET("/movie/{id}")
        void movieDetails(@Query("api_key") String apiKey, @Path("id") int movieID, Callback<MovieDetailEntity> callback);

        //TOP RATED MOVIES
        @GET("/movie/top_rated")
        void topRatedMovies(@Query("api_key") String apiKey, Callback<GetTopRatedMoviesResponse> callback);

        //POPULAR MOVIES
        @GET("/movie/popular")
        void popularMovies(@Query("api_key") String apiKey, Callback<GetPopularMoviesResponse> callback);
    }

    public static ROUTES http() {
        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(ROUTES.class);
    }

}
