package com.android.go4lunch.apis.apiGoogleMaps.repositories;

import com.android.go4lunch.apis.apiGoogleMaps.GoogleMapsRequestConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleMapsApiClient {

    private Retrofit retrofit;

    public GoogleMapsApiClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(GoogleMapsRequestConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }
}
