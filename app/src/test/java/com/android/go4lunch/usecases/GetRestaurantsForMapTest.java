package com.android.go4lunch.usecases;

import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.in_memory_repositories.InMemoryRestaurantRepository;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetRestaurantsForMapTest {

    @Test
    public void shouldReturn1MarkerOptionsWhen1RestaurantWithGeolocationIsAvailable() {
        List<Restaurant> restaurants = this.createListOfRestaurantsForRepository(1, true);
        GetRestaurantsForMap getRestaurantsForMap = this.createGetRestaurantForMap(restaurants);
        List<MarkerOptions> results = this.getObservedResults(getRestaurantsForMap);
        assert(results.size() == 1);
        assert(results.get(0).getTitle().equals("Resto U"));
    }

    @Test
    public void shouldReturn2MarkersOptionsWhen2RestaurantsWithGeolocationAreAvailable() {
        List<Restaurant> restaurants = this.createListOfRestaurantsForRepository(2, true);
        GetRestaurantsForMap getRestaurantsForMap = this.createGetRestaurantForMap(restaurants);
        List<MarkerOptions> results = this.getObservedResults(getRestaurantsForMap);
        assert(results.size() == 2);
        assert(results.get(0).getTitle().equals("Resto U"));
    }

    @Test
    public void shouldReturnNoMarkerOptionsWhenNoRestaurantIsAvailable() {
        GetRestaurantsForMap getRestaurantsForMap =
                this.createGetRestaurantForMap(new ArrayList<>());
        List<MarkerOptions> results = this.getObservedResults(getRestaurantsForMap);
        assert(results.isEmpty());
    }

    @Test
    public void shouldReturnNoMarkerOptionsWhenRestaurantHasNoAvailableGeolocation() {
        List<Restaurant> restaurantsToSetInRepository =
                this.createListOfRestaurantsForRepository(1, false);
        GetRestaurantsForMap getRestaurantsForMap = this.createGetRestaurantForMap(restaurantsToSetInRepository);

        List<MarkerOptions> results = this.getObservedResults(getRestaurantsForMap);

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

    private GetRestaurantsForMap createGetRestaurantForMap(List<Restaurant> restaurantsInRepository) {
        InMemoryRestaurantRepository inMemoryRestaurantRepository = new InMemoryRestaurantRepository();
        inMemoryRestaurantRepository.setRestaurants(restaurantsInRepository);
        return new GetRestaurantsForMap(inMemoryRestaurantRepository);
    }

    private List<MarkerOptions> getObservedResults(GetRestaurantsForMap getRestaurantsForMap) {
        Observable<List<MarkerOptions>> observableMarkersOptions = getRestaurantsForMap
                .getRestaurantsMarkers(new Geolocation(222.2, 22.22), 1000);
        List<MarkerOptions> results = new ArrayList<>();
        observableMarkersOptions.subscribe(results::addAll);
        return results;
    }


}
