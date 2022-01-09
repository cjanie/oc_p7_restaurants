package com.android.go4lunch.apiGoogleMaps;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public interface RetrofitForGoogleMapsApiService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GoogleMapsRequestConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

}
