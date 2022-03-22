package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.RestaurantGateway;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.gateways.DistanceGateway;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.usecases.decorators.DistanceInfoDecorator;
import com.android.go4lunch.usecases.decorators.SelectionInfoDecoratorForRestaurant;
import com.android.go4lunch.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForListUseCase {

    private final RestaurantGateway restaurantGateway;

    private final TimeInfoDecorator timeInfoDecorator;

    private final DistanceInfoDecorator distanceInfoDecorator;

    private final SelectionInfoDecoratorForRestaurant selectionInfoDecorator;

    public GetRestaurantsForListUseCase(
            RestaurantGateway restaurantGateway,
            TimeProvider timeProvider,
            DateProvider dateProvider,
            DistanceGateway distanceGateway,
            SelectionGateway selectionGateway
    ) {
        this.restaurantGateway = restaurantGateway;
        this.timeInfoDecorator = new TimeInfoDecorator(timeProvider, dateProvider);
        this.distanceInfoDecorator = new DistanceInfoDecorator(distanceGateway);
        this.selectionInfoDecorator = new SelectionInfoDecoratorForRestaurant(selectionGateway);
    }

    public Observable<List<RestaurantVO>> getRestaurantsWithSelections(Geolocation myPosition, int radius) {
        return this.getRestaurantsNearbyAsValueObjectWithDistance(myPosition, radius)
                .flatMap(restaurants ->
                        Observable.fromIterable(restaurants)
                                .flatMap(restaurant ->
                                        this.selectionInfoDecorator.decor(restaurant)
                                )
                                .toList().toObservable()
                        );
    }

    private Observable<List<RestaurantVO>> getRestaurantsNearbyAsValueObjectWithDistance(Geolocation myPosition, int radius) {
        return getRestaurantsNearbyAsValueObject(myPosition, radius)
                .flatMap(restaurants ->
                        Observable.fromIterable(restaurants)
                                .flatMap(restaurant ->
                                        this.distanceInfoDecorator.decor(myPosition,restaurant)
                                )
                                .toList().toObservable()
                );
    }

    private Observable<List<RestaurantVO>> getRestaurantsNearbyAsValueObject(Geolocation myPosition, int radius) {
        return handleRestaurants(myPosition, radius)
                .flatMap(restaurants ->
                        Observable.just(formatRestaurants(restaurants))
                );
    }

    private Observable<List<Restaurant>> handleRestaurants(Geolocation myPosition, int radius) {
        return this.restaurantGateway.getRestaurantsNearbyWithDetails(myPosition, radius);
    }

    private List<RestaurantVO> formatRestaurants(List<Restaurant> restaurants) {
        List<RestaurantVO> restaurantVOs = new ArrayList<>();
        if(!restaurants.isEmpty()) {
            for(Restaurant restaurant: restaurants) {
                RestaurantVO restaurantVO = new RestaurantVO(restaurant);
                this.timeInfoDecorator.decor(restaurantVO);
                this.selectionInfoDecorator.decor(restaurantVO);
                restaurantVOs.add(restaurantVO);
            }
        }
        return restaurantVOs;
    }

}
