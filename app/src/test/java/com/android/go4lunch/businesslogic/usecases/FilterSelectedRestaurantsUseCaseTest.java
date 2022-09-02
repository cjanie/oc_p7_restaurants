package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.models.RestaurantModel;
import com.android.go4lunch.businesslogic.usecases.restaurant.FilterSelectedRestaurantsUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.FindVisitorsUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.GetRestaurantsNearbyUseCase;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.in_memory_gateways.InMemoryRestaurantGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryVisitorGateway;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class FilterSelectedRestaurantsUseCaseTest {

    @Test
    public void filterSelectedRestaurantsReturnsRestaurantsHavingVisitorsWhenThereAreSome() {

        InMemoryRestaurantGateway restaurantGateway = new InMemoryRestaurantGateway();
        Restaurant restaurant1 = new Restaurant("Jojo", "rue Lol");
        restaurant1.setId("restoId1");
        restaurant1.setGeolocation(new Geolocation(1.1, 1.1));
        restaurantGateway.setRestaurants(Arrays.asList(restaurant1));

        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        Selection selection1 = new Selection("restoId1", "workmate1");
        Selection selection2 = new Selection("restoId1", "workmate2");
        selection1.setId("idSelection1");
        selection2.setId("idSelection2");
        visitorGateway.setSelections(Arrays.asList(selection1));

        GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase = new GetRestaurantsNearbyUseCase(restaurantGateway, visitorGateway);


        FilterSelectedRestaurantsUseCase useCase = new FilterSelectedRestaurantsUseCase(getRestaurantsNearbyUseCase);
        List<RestaurantValueObject> results = new ArrayList<>();
        useCase.handle(1.1, 1.1, 1).subscribe(results::addAll);
        assert(!results.isEmpty());


    }

    @Test
    public void filterSelectedRestaurantsIsEmptyWhenNoRestaurantIsSelected() {
        InMemoryRestaurantGateway restaurantGateway = new InMemoryRestaurantGateway();
        Restaurant restaurant1 = new Restaurant("Jojo", "rue Lol");
        restaurant1.setId("restoId1");
        restaurant1.setGeolocation(new Geolocation(1.1, 1.1));
        restaurantGateway.setRestaurants(Arrays.asList(restaurant1));

        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();

        GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase = new GetRestaurantsNearbyUseCase(restaurantGateway, visitorGateway);
        FilterSelectedRestaurantsUseCase useCase = new FilterSelectedRestaurantsUseCase(getRestaurantsNearbyUseCase);
        List<RestaurantValueObject> results = new ArrayList<>();
        useCase.handle(1.1, 1.1, 1).subscribe(results::addAll);
        assert(results.isEmpty());
    }
}
