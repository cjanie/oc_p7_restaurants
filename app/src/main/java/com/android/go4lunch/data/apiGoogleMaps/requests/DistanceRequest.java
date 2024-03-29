package com.android.go4lunch.data.apiGoogleMaps.requests;

import com.android.go4lunch.data.apiGoogleMaps.repositories.GoogleMapsRequestConfig;
import com.android.go4lunch.data.apiGoogleMaps.deserializers.distance.DistanceResponseRoot;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DistanceRequest {

    @GET(GoogleMapsRequestConfig.DISTANCE_MATRIX_ENDPOINT)
    Observable<DistanceResponseRoot> getData(
            @Query("destinations") List<String> destinations,
            @Query("origins") List<String> origins,
            @Query("key") String key
            );
}
