package com.jahanzaib.themoviedb.apicalls.usecase;

import com.jahanzaib.themoviedb.apicalls.BaseUseCase;
import com.jahanzaib.themoviedb.apicalls.BaseUseCaseCallback;
import com.jahanzaib.themoviedb.apicalls.entitymodel.ConfigurationEntity;
import com.jahanzaib.themoviedb.apicalls.service.API;
import com.jahanzaib.themoviedb.apicalls.service.response.GetImageConfigurationResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetImageConfigurationUseCase extends BaseUseCase {

    public interface GetImageConfigurationUseCaseCallback extends BaseUseCaseCallback {
        void onConfigurationDownloaded(ConfigurationEntity configurationEntity);
    }

    private String apiKey;

    public GetImageConfigurationUseCase(String apiKey, GetImageConfigurationUseCaseCallback callback) {
        super(callback);
        this.apiKey = API.API_KEY;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().configurations(apiKey, new Callback<GetImageConfigurationResponse>() {
            @Override
            public void success(GetImageConfigurationResponse getImageConfigurationResponse, Response response) {
                ((GetImageConfigurationUseCaseCallback) callback).onConfigurationDownloaded(getImageConfigurationResponse.getImages());
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
