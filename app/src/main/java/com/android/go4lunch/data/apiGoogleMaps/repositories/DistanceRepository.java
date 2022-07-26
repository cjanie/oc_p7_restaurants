package com.android.go4lunch.data.apiGoogleMaps.repositories;

import android.util.Log;

import com.android.go4lunch.data.apiGoogleMaps.GoogleMapsHttpClientProvider;
import com.android.go4lunch.data.apiGoogleMaps.exceptions.DistanceRequestException;
import com.android.go4lunch.data.apiGoogleMaps.requests.DistanceRequest;
import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.data.apiGoogleMaps.deserializers.distance.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DistanceRepository {

    private String TAG = "DISTANCE REPOSITORY";

    private DistanceRequest distanceRequest;

    public DistanceRepository(GoogleMapsHttpClientProvider httpClientProvider) {
        this.distanceRequest = httpClientProvider.getRetrofit().create(DistanceRequest.class);
    }

    public Observable<Integer> getDistanceInMeter(
            String destination,
            String origin
    ) {

        // Prepare parameters of the request
        List<String> destinations = new ArrayList<>();
        destinations.add(destination);
        List<String> origins = new ArrayList<>();
        origins.add(origin);
        // get data from request
        return this.distanceRequest.getData(
                destinations,
                origins,
                GoogleMapsRequestConfig.API_KEY
        )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError((error -> {
                    Log.e(TAG, error.getMessage());
                    throw new DistanceRequestException();
                }))
                .map(root -> {
                    Integer distance = null;
                    if(!root.getRows().isEmpty()) {
                        List<Element> elements = root.getRows().get(0).getElements();

                        if(elements == null)
                            throw new NullDistanceResponseException();

                        if(!elements.isEmpty()) {

                            if(elements.get(0).getDistance() == null)
                                throw new NullDistanceResponseException();
                            distance = elements.get(0).getDistance().getValue();
                        }
                    }
                    return distance;
                });
    }

}
