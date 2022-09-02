package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.in_memory_gateways.InMemoryDistanceGateway;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetDistanceFromMyPositionToRestaurantUseCaseTest {

    @Test
    public void returnsTheDistanceBetweenMyPositionAndTheRestaurantLocation() {
        InMemoryDistanceGateway distanceGateway = new InMemoryDistanceGateway(Observable.just(1L));
        GetDistanceFromMyPositionToRestaurantUseCase distanceUsecase = new GetDistanceFromMyPositionToRestaurantUseCase(distanceGateway);
        Geolocation myPosition = new Geolocation(0.5, 0.2);
        Geolocation restaurantLocation = new Geolocation(0.8, 0.7);
        List<Long> results = new ArrayList<>();
        distanceUsecase.handle(myPosition, restaurantLocation).subscribe(distance -> {
                    results.add(distance);
        });

        assert(results.get(0) == 1L);
    }
}
