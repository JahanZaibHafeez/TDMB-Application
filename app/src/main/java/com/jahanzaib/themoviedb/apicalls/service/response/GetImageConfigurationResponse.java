package com.jahanzaib.themoviedb.apicalls.service.response;


import com.jahanzaib.themoviedb.apicalls.entitymodel.ConfigurationEntity;

public class GetImageConfigurationResponse {

    private ConfigurationEntity images;

    public ConfigurationEntity getImages() {
        return images;
    }

    public void setImages(ConfigurationEntity images) {
        this.images = images;
    }
}
