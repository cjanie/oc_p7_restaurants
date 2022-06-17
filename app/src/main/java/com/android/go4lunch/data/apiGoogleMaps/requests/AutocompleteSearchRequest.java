package com.android.go4lunch.data.apiGoogleMaps.requests;

import com.android.go4lunch.data.apiGoogleMaps.deserializers.place.PlaceResponseRoot;
import com.android.go4lunch.data.apiGoogleMaps.repositories.GoogleMapsRequestConfig;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AutocompleteSearchRequest {

    @GET(GoogleMapsRequestConfig.AUTOCOMPLETE_SEARCH_END_POINT)
    Observable<PlaceResponseRoot> getData(
            @Query("key") String key
    );
}
