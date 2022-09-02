package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.usecases.restaurant.GetRestaurantsNearbyUseCase;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.in_memory_gateways.InMemoryRestaurantGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryVisitorGateway;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class GetRestaurantsNearbyUseCaseTest {

    private List<Restaurant> createRestaurantList(int numberOfRestaurants, boolean hasGeolocation) {
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

    private GetRestaurantsNearbyUseCase createUseCase(List<Restaurant> restaurantsInRepository) {
        InMemoryRestaurantGateway restaurantGateway = new InMemoryRestaurantGateway();
        restaurantGateway.setRestaurants(restaurantsInRepository);
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        return new GetRestaurantsNearbyUseCase(restaurantGateway, visitorGateway);
    }

    private List<RestaurantValueObject> getSUTResults(GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase) {
        Observable<List<RestaurantValueObject>> restaurantsObservable = getRestaurantsNearbyUseCase
                .handle(222.2, 22.22, 1000);
        List<RestaurantValueObject> results = new ArrayList<>();
        restaurantsObservable.subscribe(results::addAll);
        return results;
    }

    @Test
    public void returns1RestaurantWhen1RestaurantIsAvailable() {
        List<Restaurant> restaurants = this.createRestaurantList(1, true);
        GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase = this.createUseCase(restaurants);
        List<RestaurantValueObject> results = this.getSUTResults(getRestaurantsNearbyUseCase);
        assert(results.size() == 1);
    }

    @Test
    public void returns2RestaurantsWhen2RestaurantsAreAvailable() {
        List<Restaurant> restaurants = this.createRestaurantList(2, true);
        GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase = this.createUseCase(restaurants);
        List<RestaurantValueObject> results = this.getSUTResults(getRestaurantsNearbyUseCase);
        assert(results.size() == 2);
    }

    @Test
    public void returnsNoRestaurantWhenNoneIsAvailable() {
        GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase =
                this.createUseCase(new ArrayList<>());
        List<RestaurantValueObject> results = this.getSUTResults(getRestaurantsNearbyUseCase);
        assert(results.isEmpty());
    }

    @Test
    public void restaurantCanHaveSelections() {
        Restaurant restaurant1 = new Restaurant("Chez Jojo", "adresse");
        restaurant1.setId("resto1");
        restaurant1.setGeolocation(new Geolocation(1.1, 1.1));
        Restaurant restaurant2 = new Restaurant("Chez Janvier", "adresse");
        restaurant2.setId("resto2");
        restaurant2.setGeolocation(new Geolocation(1.2, 1.2));

        InMemoryRestaurantGateway restaurantGateway = new InMemoryRestaurantGateway();
        restaurantGateway.setRestaurants(Arrays.asList(restaurant1, restaurant2));

        Selection selection1Resto2 = new Selection("resto2", "workmate1");
        Selection selection2Resto2 = new Selection("resto2", "workmate2");

        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        visitorGateway.setSelections(Arrays.asList(selection1Resto2, selection2Resto2));

        // SUT
        GetRestaurantsNearbyUseCase useCase = new GetRestaurantsNearbyUseCase(restaurantGateway, visitorGateway);
        assertEquals(0, this.getSUTResults(useCase).get(0).getVisitorsCount());
        assertEquals(2, this.getSUTResults(useCase).get(1).getVisitorsCount());
    }
}
