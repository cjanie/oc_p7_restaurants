package com.android.go4lunch;

import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.Info;
import com.android.go4lunch.read.businesslogic.usecases.model.CustomLocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;

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

    @Test
    public void shouldReturnOneRestaurantWhenThereIsOneAvailable() {
        Restaurant restaurant = new Restaurant("Aa", new CustomLocation("rue neuve"));
        this.initWithSomeRestaurants(Arrays.asList(new Restaurant[] {restaurant}));

        assert(new RetrieveRestaurants(this.restaurantQuery).handle().size() == 1);
        assert(new RetrieveRestaurants(this.restaurantQuery).handle().get(0).getName().equals("Aa"));
        assertNotNull(new RetrieveRestaurants(this.restaurantQuery).handle().get(0).getLocation().getAddress().equals("rue neuve"));
    }

    @Test
    public void shouldReturnTwoRestaurantsIfTwoAreAvailable() {
        Restaurant restaurant1 = new Restaurant("Aa", new CustomLocation(""));
        Restaurant restaurant2 = new Restaurant("Bb", new CustomLocation(""));
        this.initWithSomeRestaurants(Arrays.asList(new Restaurant[] {restaurant1, restaurant2}));

        assert (new RetrieveRestaurants(this.restaurantQuery).handle().size() == 2);
    }

    @Test
    public void shoudlBeEmptyIfNoRestaurantIsAvailable() {
        InMemoryRestaurantQuery restaurantQuery = new InMemoryRestaurantQuery();
        assert (new RetrieveRestaurants(restaurantQuery).handle().isEmpty());
    }

    @Test
    public void shouldReturnCLOSEIfCLOSE() {
        Restaurant restaurant1 = new Restaurant("Aa", new CustomLocation(""));
        restaurant1.setOpen(LocalTime.of(9, 0));
        restaurant1.setClose(LocalTime.of(21,0));
        this.initWithSomeRestaurants(Arrays.asList(new Restaurant[] {restaurant1}));
        DeterministicTimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(6, 0));
        assert(new RetrieveRestaurants(this.restaurantQuery).handleVO(timeProvider).get(0).getInfo().equals(Info.CLOSE));
    }

    @Test
    public void shouldReturnOPENIfOPEN() {
        Restaurant restaurant1 = new Restaurant("Aa", new CustomLocation(""));
        restaurant1.setOpen(LocalTime.of(9, 0));
        restaurant1.setClose(LocalTime.of(21,0));
        this.initWithSomeRestaurants(Arrays.asList(new Restaurant[] {restaurant1}));
        DeterministicTimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(12, 0));
        assert(new RetrieveRestaurants(this.restaurantQuery).handleVO(timeProvider).get(0).getInfo().equals(Info.OPEN));
    }

    @Test
    public void shouldReturnOPENINGSOONIfOPENINGSOON() {
        Restaurant restaurant1 = new Restaurant("Aa", new CustomLocation(""));
        restaurant1.setOpen(LocalTime.of(9, 0));
        restaurant1.setClose(LocalTime.of(21,0));
        this.initWithSomeRestaurants(Arrays.asList(new Restaurant[] {restaurant1}));
        DeterministicTimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(8, 0));
        assert(new RetrieveRestaurants(this.restaurantQuery).handleVO(timeProvider).get(0).getInfo().equals(Info.OPENING_SOON));
    }

    @Test
    public void shouldReturnCLOSINGSOONIfCLOSINGSOON() {
        Restaurant restaurant1 = new Restaurant("Aa", new CustomLocation(""));
        restaurant1.setOpen(LocalTime.of(9, 0));
        restaurant1.setClose(LocalTime.of(21,0));
        this.initWithSomeRestaurants(Arrays.asList(new Restaurant[] {restaurant1}));
        DeterministicTimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(20, 0));
        assert(new RetrieveRestaurants(this.restaurantQuery).handleVO(timeProvider).get(0).getInfo().equals(Info.CLOSING_SOON));
    }

    private void initWithSomeRestaurants(List<Restaurant> restaurants) {
        this.restaurantQuery.setRestaurants(restaurants);
    }
}
