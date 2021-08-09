package com.android.go4lunch;

import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.Info;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.model.CustomLocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.TimeInfo;

import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;

public class RestaurantVOTest {

    @Test
    public void shouldReturnCLOSEIfRestaurantIsClose() {
        Restaurant restaurant = new Restaurant("r", new CustomLocation("l"));
        restaurant.setOpen(LocalTime.of(9,0));
        restaurant.setClose(LocalTime.of(23,0));

        DeterministicTimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(23,30));

        InMemoryRestaurantQuery restaurantQuery = new InMemoryRestaurantQuery();
        restaurantQuery.setRestaurants(Arrays.asList(new Restaurant[] {restaurant}));

        assert(new RetrieveRestaurants(restaurantQuery).handleVO(timeProvider).get(0).getInfo().equals(Info.CLOSE));
    }

    @Test
    public void shouldReturnOPENIfRestaurantIsOpen() {
        Restaurant restaurant = new Restaurant("r", new CustomLocation("l"));
        restaurant.setOpen(LocalTime.of(9,0));
        restaurant.setClose(LocalTime.of(23,0));

        DeterministicTimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(12,30));

        InMemoryRestaurantQuery restaurantQuery = new InMemoryRestaurantQuery();
        restaurantQuery.setRestaurants(Arrays.asList(new Restaurant[] {restaurant}));

        assert(new RetrieveRestaurants(restaurantQuery).handleVO(timeProvider).get(0).getInfo().equals(Info.OPEN));
    }

    @Test
    public void shouldReturnOPENINGSOONIfRestaurantIsOpenningSoon() {
        Restaurant restaurant = new Restaurant("r", new CustomLocation("l"));
        restaurant.setOpen(LocalTime.of(9,0));
        restaurant.setClose(LocalTime.of(23,0));

        DeterministicTimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(8,0));

        InMemoryRestaurantQuery restaurantQuery = new InMemoryRestaurantQuery();
        restaurantQuery.setRestaurants(Arrays.asList(new Restaurant[] {restaurant}));

        assert(new RetrieveRestaurants(restaurantQuery).handleVO(timeProvider).get(0).getInfo().equals(Info.OPENING_SOON));
    }
}
