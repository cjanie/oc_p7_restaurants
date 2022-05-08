package com.android.go4lunch.usecases;

import com.android.go4lunch.deterministic_providers.DeterministicDateProvider;
import com.android.go4lunch.deterministic_providers.DeterministicTimeProvider;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.usecases.enums.TimeInfo;
import com.android.go4lunch.usecases.models.RestaurantModel;
import com.android.go4lunch.models.Restaurant;


import org.junit.Test;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class TimeInfoDecoratorTest {

    // assertion method

    private void assertCases(
            int today,
            LocalTime now,
            LocalTime openingTime,
            LocalTime closingTime,
            TimeInfo expectedTimeInfo
    ) {
        DateProvider dateProvider = new DeterministicDateProvider(today);
        TimeProvider timeProvider = new DeterministicTimeProvider(now);
        TimeInfoDecorator timeInfoDecorator = new TimeInfoDecorator(timeProvider, dateProvider);

        // Prepare planning for a restaurant
        Map<Integer, Map<String, LocalTime>> planning = new HashMap<>();
        // Prepare the times map of the planning
        Map<String, LocalTime> times = new HashMap<>();
        times.put("open", openingTime);
        times.put("close", closingTime);
        // Set the planning
        planning.put(today, times);
        Restaurant restaurant = new Restaurant("Los Rest", "2 rue des Pirouettes");
        restaurant.setPlanning(planning);
        // Instantiate Parameter for test

        // Under test
        TimeInfo actual = timeInfoDecorator.decor(restaurant);
        // Assertion
        assert(actual.equals(expectedTimeInfo));
    }

    @Test
    public void shouldReturnCloseNowAt6TodayOnMondayBeforeOpening() {
        this.assertCases(1, // today
                LocalTime.of(6, 0), // now
                LocalTime.of(8, 0), // open
                LocalTime.of(18, 30), // close
                TimeInfo.CLOSE
        );
    }

    @Test
    public void shouldReturnOpentNowAt9TodayOnMonday() {
        this.assertCases(
                1,
                LocalTime.of(9, 0),
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                TimeInfo.OPEN
        );
    }

    @Test
    public void shouldReturnCloseNowAt19TodayOnMondayAfterClosing() {
        this.assertCases(
                1,
                LocalTime.of(19, 0),
                LocalTime.of(8, 0),
                LocalTime.of(18, 0),
                TimeInfo.CLOSE
                );
    }

    @Test
    public void shouldReturnOpenNowAt9TodayOnTuesday() {
        this.assertCases(2,
                LocalTime.of(9, 0),
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                TimeInfo.OPEN
                );
    }

    @Test
    public void shouldReturnClosingSoonNowAt1830TodayOnTuesday() {
        this.assertCases(2,
                LocalTime.of(18, 30),
                LocalTime.of(8, 0),
                LocalTime.of(19, 30),
                TimeInfo.CLOSING_SOON
                );
    }



    @Test
    public void shouldReturnDefaultWhenThereIsNoPlanning() {
        // Instantiate Parameter for test
        Restaurant restaurant = new Restaurant("Los Rest", "2 rue des Pirouettes");
        // Under test
        TimeInfoDecorator timeInfoDecorator = new TimeInfoDecorator(
                new DeterministicTimeProvider(LocalTime.of(12, 0)),
                new DeterministicDateProvider(1)
        );
        TimeInfo actual = timeInfoDecorator.decor(restaurant);
        assert(actual.equals(TimeInfo.DEFAULT_TIME_INFO));
    }
}
