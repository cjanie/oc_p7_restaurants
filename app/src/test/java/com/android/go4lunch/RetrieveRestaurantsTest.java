package com.android.go4lunch;

import com.android.go4lunch.read.adapter.DeterministicGeolocationProvider;
import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.Info;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.model.TimeInfo;

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
    public void shoudlBeEmptyIfNoRestaurantIsAvailable() {
        InMemoryRestaurantQuery restaurantQuery = new InMemoryRestaurantQuery();
        assert (new RetrieveRestaurants(restaurantQuery).handle().isEmpty());
    }

    @Test
    public void shouldReturnCLOSEIfCLOSE() {
        this.checkMatchesTimeInfo(LocalTime.of(9, 0), LocalTime.of(21,0),
                LocalTime.of(6, 0), Info.CLOSE);
        }

    @Test
    public void shouldReturnOPENIfOPEN() {
        this.checkMatchesTimeInfo(LocalTime.of(9, 0), LocalTime.of(21,0),
                LocalTime.of(12, 0), Info.OPEN);
    }

    @Test
    public void shouldReturnOPENINGSOONIfOPENINGSOON() {
        this.checkMatchesTimeInfo(LocalTime.of(9, 0), LocalTime.of(21,0),
                LocalTime.of(8, 0), Info.OPENING_SOON);
    }

    @Test
    public void shouldReturnCLOSINGSOONIfCLOSINGSOON() {
        this.checkMatchesTimeInfo(LocalTime.of(9, 0), LocalTime.of(21,0),
                LocalTime.of(20, 0), Info.CLOSING_SOON);
    }

    private void checkMatchesTimeInfo(LocalTime open, LocalTime close, LocalTime now, Info info) {
        Restaurant restaurant = new Restaurant("Aa", "");
        restaurant.setOpen(open);
        restaurant.setClose(close);
        this.initWithSomeRestaurants(Arrays.asList(new Restaurant[] {restaurant}));
        DeterministicTimeProvider timeProvider = new DeterministicTimeProvider(now);
        DeterministicGeolocationProvider geolocationProvider = new DeterministicGeolocationProvider(new Geolocation(0D, 0D));
        assert(new TimeInfo(timeProvider, new RetrieveRestaurants(this.restaurantQuery).handleVO().get(0)).getInfo().equals(info));
    }

    private void initWithSomeRestaurants(List<Restaurant> restaurants) {
        this.restaurantQuery.setRestaurants(restaurants);
    }


}
