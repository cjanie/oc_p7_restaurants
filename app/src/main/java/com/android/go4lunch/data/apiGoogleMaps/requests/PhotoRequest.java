package com.android.go4lunch.data.apiGoogleMaps.requests;

import com.android.go4lunch.data.apiGoogleMaps.repositories.GoogleMapsRequestConfig;
import com.android.go4lunch.data.apiGoogleMaps.deserializers.place.PhotoResponseRoot;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoRequest {

    @GET(GoogleMapsRequestConfig.PHOTO_ENDPOINT)
    Observable<PhotoResponseRoot> getData(
        @Query("maxwidth") int maxWidth,
        @Query("photo_reference") String photoReference,
        @Query("key") String key
    );
}
