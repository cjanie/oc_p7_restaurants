package com.android.go4lunch.data.apiGoogleMaps.requests;

import com.android.go4lunch.data.apiGoogleMaps.repositories.GoogleMapsRequestConfig;
import com.android.go4lunch.data.apiGoogleMaps.deserializers.place.DetailsResponseRoot;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DetailsRequest {

    @GET(GoogleMapsRequestConfig.DETAILS_ENDPOINT)
    public Observable<DetailsResponseRoot> getData(
            @Query("place_id") String placeId,
            @Query("fields") String fields,
            @Query("key") String key
    );

}
