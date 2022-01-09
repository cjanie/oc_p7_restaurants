package com.android.go4lunch.apiGoogleMaps;

import com.android.go4lunch.apiGoogleMaps.deserialized_entities.DetailsResponseRoot;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DetailsService extends RetrofitForGoogleMapsApiService {

    @GET(GoogleMapsRequestConfig.DETAILS_ENDPOINT)
    public Observable<DetailsResponseRoot> getData(
            @Query("place_id") String placeId,
            @Query("fields") String fields,
            @Query("key") String key
    );

}
