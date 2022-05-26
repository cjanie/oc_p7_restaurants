package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.in_memory_gateways.InMemoryRestaurantGateway;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetRestaurantsNearbyUseCaseTest {

    @Test
    public void shouldReturn1MarkerOptionsWhen1RestaurantWithGeolocationIsAvailable() {
        List<Restaurant> restaurants = this.createListOfRestaurantsForRepository(1, true);
        GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase = this.createGetRestaurantForMap(restaurants);
        List<Restaurant> results = this.getObservedResults(getRestaurantsNearbyUseCase);
        assert(results.size() == 1);
        assert(results.get(0).getName().equals("Resto U"));
    }

    @Test
    public void shouldReturn2MarkersOptionsWhen2RestaurantsWithGeolocationAreAvailable() {
        List<Restaurant> restaurants = this.createListOfRestaurantsForRepository(2, true);
        GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase = this.createGetRestaurantForMap(restaurants);
        List<Restaurant> results = this.getObservedResults(getRestaurantsNearbyUseCase);
        assert(results.size() == 2);
        assert(results.get(0).getName().equals("Resto U"));
    }

    @Test
    public void shouldReturnNoMarkerOptionsWhenNoRestaurantIsAvailable() {
        GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase =
                this.createGetRestaurantForMap(new ArrayList<>());
        List<Restaurant> results = this.getObservedResults(getRestaurantsNearbyUseCase);
        assert(results.isEmpty());
    }

    private List<Restaurant> createListOfRestaurantsForRepository(int numberOfRestaurants, boolean hasGeolocation) {
        List<Restaurant> restaurants = new ArrayList<>();
        while (restaurants.size() < numberOfRestaurants) {
            Restaurant restaurant = new Restaurant("Resto U", "Allée de Vert Bois");
            if(hasGeolocation) {
                restaurant.setGeolocation(new Geolocation(111.11, 11.11));
            }
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    private GetRestaurantsNearbyUseCase createGetRestaurantForMap(List<Restaurant> restaurantsInRepository) {
        InMemoryRestaurantGateway inMemoryRestaurantQuery = new InMemoryRestaurantGateway();
        inMemoryRestaurantQuery.setRestaurants(restaurantsInRepository);
        return new GetRestaurantsNearbyUseCase(inMemoryRestaurantQuery);
    }

    private List<Restaurant> getObservedResults(GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase) {
        Observable<List<Restaurant>> restaurantsObservable = getRestaurantsNearbyUseCase
                .handle(222.2, 22.22, 1000);
        List<Restaurant> results = new ArrayList<>();
        restaurantsObservable.subscribe(results::addAll);
        return results;
    }


}
