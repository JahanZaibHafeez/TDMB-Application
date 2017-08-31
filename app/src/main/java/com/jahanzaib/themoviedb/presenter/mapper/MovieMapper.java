package com.jahanzaib.themoviedb.presenter.mapper;


import android.text.TextUtils;

import com.jahanzaib.themoviedb.apicalls.BaseMapper;
import com.jahanzaib.themoviedb.apicalls.entitymodel.CompanyEntity;
import com.jahanzaib.themoviedb.apicalls.entitymodel.MovieDetailEntity;
import com.jahanzaib.themoviedb.apicalls.entitymodel.MovieEntity;
import com.jahanzaib.themoviedb.presenter.Utility;
import com.jahanzaib.themoviedb.presenter.mvp.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MovieMapper extends BaseMapper<MovieEntity, MovieModel> {

    @Override
    public MovieModel toModel(MovieEntity entity) {
        MovieModel movieModel = new MovieModel();

        movieModel.setId(entity.getId());
        movieModel.setName(entity.getTitle());
        movieModel.setYearOfRelease(Utility.getYearFromServerDate(entity.getRelease_date()));

        if(!TextUtils.isEmpty(entity.getPoster_path())) {
            movieModel.setSmallCover(Utility.buildCompleteImageURL(entity.getPoster_path(), "w154"));
            movieModel.setBigCover(Utility.buildCompleteImageURL(entity.getPoster_path(), "original"));
        }

        return movieModel;
    }

    @Override
    public MovieModel deserializeModel(String serializedModel) {
        return gson.fromJson(serializedModel, MovieModel.class);
    }

    public MovieModel addDetails(MovieModel receiver, MovieDetailEntity detailsToBeInserted) {

        receiver.setHomepage(detailsToBeInserted.getHomepage());

        List<String> companies = new ArrayList<>();
        for(CompanyEntity company : detailsToBeInserted.getProduction_companies()) {
            companies.add(company.getName());
        }
        //remove the first and the last character
        receiver.setCompanies(companies.toString().substring(1,companies.toString().length()-1));

        receiver.setTagline(detailsToBeInserted.getTagline());
        receiver.setOverview(detailsToBeInserted.getOverview());

        return receiver;
    }
}
