package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.enums.TimeInfo;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.decorators.TimeInfoDecorator;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class RetrieveRestaurantsTest {

    private InMemoryRestaurantQuery restaurantQuery;

    @Before
    public void setUp() {
        this.restaurantQuery = new InMemoryRestaurantQuery();
    }

    private void initWithSomeRestaurants(List<Restaurant> restaurants) {
        this.restaurantQuery.setRestaurants(restaurants);
    }

    @Test
    public void shouldReturnOneRestaurantWhenThereIsOneAvailable() {
        Restaurant restaurant = new Restaurant("Aa", "rue neuve");
        this.initWithSomeRestaurants(Arrays.asList(new Restaurant[] {restaurant}));

        assert(new RetrieveRestaurants(this.restaurantQuery).handle().size() == 1);
        assert(new RetrieveRestaurants(this.restaurantQuery).handle().get(0).getName().equals("Aa"));
        assertNotNull(new RetrieveRestaurants(this.restaurantQuery).handle().get(0).getAddress().equals("rue neuve"));
    }

    @Test
    public void shouldReturnTwoRestaurantsIfTwoAreAvailable() {
        Restaurant restaurant1 = new Restaurant("Aa", "");
        Restaurant restaurant2 = new Restaurant("Bb", "");
        this.initWithSomeRestaurants(Arrays.asList(new Restaurant[] {restaurant1, restaurant2}));

        assert (new RetrieveRestaurants(this.restaurantQuery).handle().size() == 2);
    }

    @Test
    public void shouldBeEmptyIfNoRestaurantIsAvailable() {
        InMemoryRestaurantQuery restaurantQuery = new InMemoryRestaurantQuery();
        assert (new RetrieveRestaurants(restaurantQuery).handle().isEmpty());
    }

}
