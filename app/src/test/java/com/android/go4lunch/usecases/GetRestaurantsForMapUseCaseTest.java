package com.android.go4lunch.usecases;

import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.in_memory_gateways.InMemoryRestaurantGateway;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetRestaurantsForMapUseCaseTest {

    @Test
    public void shouldReturn1MarkerOptionsWhen1RestaurantWithGeolocationIsAvailable() {
        List<Restaurant> restaurants = this.createListOfRestaurantsForRepository(1, true);
        GetRestaurantsForMapUseCase getRestaurantsForMapUseCase = this.createGetRestaurantForMap(restaurants);
        List<MarkerOptions> results = this.getObservedResults(getRestaurantsForMapUseCase);
        assert(results.size() == 1);
        assert(results.get(0).getTitle().equals("Resto U"));
    }

    @Test
    public void shouldReturn2MarkersOptionsWhen2RestaurantsWithGeolocationAreAvailable() {
        List<Restaurant> restaurants = this.createListOfRestaurantsForRepository(2, true);
        GetRestaurantsForMapUseCase getRestaurantsForMapUseCase = this.createGetRestaurantForMap(restaurants);
        List<MarkerOptions> results = this.getObservedResults(getRestaurantsForMapUseCase);
        assert(results.size() == 2);
        assert(results.get(0).getTitle().equals("Resto U"));
    }

    @Test
    public void shouldReturnNoMarkerOptionsWhenNoRestaurantIsAvailable() {
        GetRestaurantsForMapUseCase getRestaurantsForMapUseCase =
                this.createGetRestaurantForMap(new ArrayList<>());
        List<MarkerOptions> results = this.getObservedResults(getRestaurantsForMapUseCase);
        assert(results.isEmpty());
    }

    @Test
    public void shouldReturnNoMarkerOptionsWhenRestaurantHasNoAvailableGeolocation() {
        List<Restaurant> restaurantsToSetInRepository =
                this.createListOfRestaurantsForRepository(1, false);
        GetRestaurantsForMapUseCase getRestaurantsForMapUseCase = this.createGetRestaurantForMap(restaurantsToSetInRepository);

        List<MarkerOptions> results = this.getObservedResults(getRestaurantsForMapUseCase);

        assert(results.isEmpty());
    }

    private List<Restaurant> createListOfRestaurantsForRepository(int numberOfRestaurants, boolean hasGeolocation) {
        List<Restaurant> restaurants = new ArrayList<>();
        while (restaurants.size() < numberOfRestaurants) {
            Restaurant restaurant = new Restaurant("Resto U", "All??e de Vert Bois");
            if(hasGeolocation) {
                restaurant.setGeolocation(new Geolocation(111.11, 11.11));
            }
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    private GetRestaurantsForMapUseCase createGetRestaurantForMap(List<Restaurant> restaurantsInRepository) {
        InMemoryRestaurantGateway inMemoryRestaurantQuery = new InMemoryRestaurantGateway();
        inMemoryRestaurantQuery.setRestaurants(restaurantsInRepository);
        return new GetRestaurantsForMapUseCase(inMemoryRestaurantQuery);
    }

    private List<MarkerOptions> getObservedResults(GetRestaurantsForMapUseCase getRestaurantsForMapUseCase) {
        Observable<List<MarkerOptions>> observableMarkersOptions = getRestaurantsForMapUseCase
                .getRestaurantsMarkers(new Geolocation(222.2, 22.22), 1000);
        List<MarkerOptions> results = new ArrayList<>();
        observableMarkersOptions.subscribe(results::addAll);
        return results;
    }


}
