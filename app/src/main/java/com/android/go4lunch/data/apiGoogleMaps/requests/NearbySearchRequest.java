package com.android.go4lunch.data.apiGoogleMaps.requests;

import com.android.go4lunch.data.apiGoogleMaps.repositories.GoogleMapsRequestConfig;
import com.android.go4lunch.data.apiGoogleMaps.deserializers.place.PlaceResponseRoot;

import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NearbySearchRequest {

    @GET(GoogleMapsRequestConfig.NEARBY_SEARCH_END_POINT)
    Observable<PlaceResponseRoot> getData(
            @Query("location") String location,
            @Query("radius") int radius,
            @Query("type") String type,
            @Query("fields") String fields,
            @Query("key") String key
    );
}
