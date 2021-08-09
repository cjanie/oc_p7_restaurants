package com.android.go4lunch;

import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.Info;
import com.android.go4lunch.read.businesslogic.usecases.model.CustomLocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.TimeInfo;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

public class TimeInfoTest {

    @Before
    public void setUp() {

    }

    @Test
    public void shouldShowThatRestaurantIsOpen() {
        Restaurant restaurant = new Restaurant("R1", new CustomLocation("L"));
        restaurant.setOpen(LocalTime.of(9,0));
        restaurant.setClose(LocalTime.of(20, 30));
        TimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(12, 0));
        assert(new TimeInfo(timeProvider).handle(restaurant).equals(Info.OPEN));
    }

    @Test
    public void shouldShowThatRestaurantIsClose() {
        Restaurant restaurant = new Restaurant("R1", new CustomLocation("L"));
        restaurant.setOpen(LocalTime.of(9,0));
        restaurant.setClose(LocalTime.of(20, 30));
        TimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(23, 0));
        assert(new TimeInfo(timeProvider).handle(restaurant).equals(Info.CLOSE));
    }

    @Test
    public void shouldShowCloseIf1AMAndRestaurantOpensAt9AM() {
        Restaurant restaurant = new Restaurant("R1", new CustomLocation("L"));
        restaurant.setOpen(LocalTime.of(9,0));
        restaurant.setClose(LocalTime.of(20, 30));
        TimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(1, 0));
        assert(new TimeInfo(timeProvider).handle(restaurant).equals(Info.CLOSE));
    }

    @Test
    public void shouldShowCloseSoon1HourBeforeClosing() {
        Restaurant restaurant = new Restaurant("R1", new CustomLocation("L"));
        restaurant.setOpen(LocalTime.of(9,0));
        restaurant.setClose(LocalTime.of(20, 30));
        TimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(19, 35));
        assert(new TimeInfo(timeProvider).handle(restaurant).equals(Info.CLOSING_SOON));
    }

    @Test
    public void shouldShowCloseSoon1HourExactlyBeforeClosing() {
        Restaurant restaurant = new Restaurant("R1", new CustomLocation("L"));
        restaurant.setOpen(LocalTime.of(9,0));
        restaurant.setClose(LocalTime.of(20, 30));
        TimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(19, 30));
        assert(new TimeInfo(timeProvider).handle(restaurant).equals(Info.CLOSING_SOON));
    }

    @Test
    public void shouldShowOpeningSoon1HourBeforeOpening() {
        Restaurant restaurant = new Restaurant("R1", new CustomLocation("L"));
        restaurant.setOpen(LocalTime.of(9,0));
        restaurant.setClose(LocalTime.of(20, 30));
        TimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(8, 35));
        assert(new TimeInfo(timeProvider).handle(restaurant).equals(Info.OPENING_SOON));
    }

    @Test
    public void shouldShowOpeningSoon1HourExactelyBeforeOpening() {
        Restaurant restaurant = new Restaurant("R1", new CustomLocation("L"));
        restaurant.setOpen(LocalTime.of(9,0));
        restaurant.setClose(LocalTime.of(20, 30));
        TimeProvider timeProvider = new DeterministicTimeProvider(LocalTime.of(8, 00));
        assert(new TimeInfo(timeProvider).handle(restaurant).equals(Info.OPENING_SOON));
    }
}
