package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.RestaurantGateway;
import com.android.go4lunch.in_memory_repositories.InMemoryRestaurantGateway;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;

public class GetRestaurantByIdUseCaseTest {

    @Test
    public void returnsRestaurantWhenFound() throws NotFoundException {
        InMemoryRestaurantGateway restaurantGateway = new InMemoryRestaurantGateway();
        Restaurant r1 = new Restaurant("Jojo", "Là");
        r1.setId("1");
        Restaurant r2 = new Restaurant("Lala", "Ici");
        r2.setId("2");
        Restaurant r3 = new Restaurant("Lol", "Là-bas");
        r3.setId("3");
        restaurantGateway.setRestaurants(Arrays.asList(r1, r2, r3));

        GetRestaurantByIdUseCase getRestaurantByIdUseCase = new GetRestaurantByIdUseCase(restaurantGateway);

        List<Restaurant> restaurantResults = new ArrayList<>();
        getRestaurantByIdUseCase.handle("3").subscribe(restaurantResults::add);
        Restaurant found = restaurantResults.get(0);
        assert(found.getId().equals("3"));

    }

    @Test
    public void returnsNothingWhenUnfound() throws NotFoundException {
        InMemoryRestaurantGateway restaurantGateway = new InMemoryRestaurantGateway();
        assertThrows(NotFoundException.class, () -> {
            new GetRestaurantByIdUseCase(restaurantGateway).handle("1");
        });
    }

}
