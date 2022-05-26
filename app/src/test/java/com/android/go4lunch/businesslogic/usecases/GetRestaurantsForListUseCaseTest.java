package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.in_memory_gateways.InMemoryRestaurantGateway;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForListUseCaseTest {


    private GetRestaurantsForListUseCase createGetRestaurantsForList(int availableRestaurantsSize) {
        // Fill repository with available restaurants
        InMemoryRestaurantGateway restaurantQuery = new InMemoryRestaurantGateway();
        List<Restaurant> availableRestaurants = new ArrayList<>();
        while(availableRestaurants.size() < availableRestaurantsSize) {
            Restaurant restaurant = new Restaurant("Restaurant de la Pointe", "97 rue Langevin");
           availableRestaurants.add(restaurant);
        }
        restaurantQuery.setRestaurants(availableRestaurants);

        return new GetRestaurantsForListUseCase(
                restaurantQuery
        );
    }

    private List<Restaurant> getObservedResult(GetRestaurantsForListUseCase getRestaurantsForListUseCase) {
        Observable<List<Restaurant>> observableRestaurants = getRestaurantsForListUseCase
                .handle(1111.1, 1111.2, 1000);
        List<Restaurant> results = new ArrayList<>();
        observableRestaurants.subscribe(results::addAll);
        return results;
    }

    @Test
    public void returnsRestaurantsWhenThereAreSomeAvailable() {

        // SUT with 2 restaurants available
        GetRestaurantsForListUseCase getRestaurantsForListUseCase = this.createGetRestaurantsForList(
                2
        );

        assert(this.getObservedResult(getRestaurantsForListUseCase).size() == 2);
    }

    @Test
    public void listShouldBeEmptyWhenNoRestaurantIsAvailable() {

        // SUT without any restaurant available
        GetRestaurantsForListUseCase getRestaurantsForListUseCase = this.createGetRestaurantsForList(
                0
        );

        assert(this.getObservedResult(getRestaurantsForListUseCase).isEmpty());
    }



}
