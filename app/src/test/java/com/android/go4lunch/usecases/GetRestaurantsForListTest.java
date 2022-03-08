package com.android.go4lunch.usecases;

import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.gateways_impl.InMemorySelectionGateway;
import com.android.go4lunch.gateways_impl.InMemoryHistoricOfSelectionsGateway;
import com.android.go4lunch.deterministic_providers.DeterministicDateProvider;
import com.android.go4lunch.deterministic_providers.DeterministicTimeProvider;
import com.android.go4lunch.usecases.enums.TimeInfo;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.in_memory_repositories.InMemoryDistanceRepository;
import com.android.go4lunch.in_memory_repositories.InMemoryRestaurantGateway;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;

import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class GetRestaurantsForListTest {

    @Test
    public void shouldReturnListOfRestaurantsWhenAvailable() {
        // Create a list of restaurants to fill the repository
        List<Restaurant> restaurantsToSetInRepository = this.createListOfRestaurants(
                2,
                false,
                false
        );
        // Create the use case under test
        GetRestaurantsForList getRestaurantsForList = this.createGetRestaurantsForList(
                LocalTime.of(12, 0), // now
                1,
                restaurantsToSetInRepository
        );
        // TEST
        List<RestaurantVO> results = this.getObservedResult(getRestaurantsForList);
        assertThat(results, notNullValue());
        assert(results.size() == 2);
    }

    @Test
    public void listShouldBeEmptyWhenNoRestaurantIsAvailable() {
        // Prepare use case under test
        GetRestaurantsForList getRestaurantsForList = this.createGetRestaurantsForList(
                LocalTime.now(),
                1,
                new ArrayList<>()
        );
        // TEST
        List<RestaurantVO> results = this.getObservedResult(getRestaurantsForList);
        assertThat(results, notNullValue());
        assert(results.isEmpty());
    }

    @Test
    public void restaurantsShouldHaveTimeInfoWhenAvailable() {
        // Make the list of restaurants to fill repository of GetRestaurantsForList
        List<Restaurant> restaurantsToSetInRepository = this.createListOfRestaurants(1,
                false,
                true
        );
        // Use case under test
        GetRestaurantsForList getRestaurantsForList = this.createGetRestaurantsForList(
                LocalTime.of(12, 0),
                1,
                restaurantsToSetInRepository
        );
        // TEST
        List<RestaurantVO> results = this.getObservedResult(getRestaurantsForList);
        assertThat(results, notNullValue());
        assert(results.size() == 1);
        assert(results.get(0).getTimeInfo().equals(TimeInfo.OPEN));
    }

    @Test
    public void restaurantsShouldNotHaveTimeInfoWhenNotAvailable() {
        // Create the list of restaurants to fill repository
        List<Restaurant> restaurantsToSetInRepository = this.createListOfRestaurants(
                1,
                false,
                false);
        // Use case under test
        GetRestaurantsForList getRestaurantsForList = this.createGetRestaurantsForList(
                LocalTime.of(12, 0),
                1,
                restaurantsToSetInRepository
        );
        // TEST
        List<RestaurantVO> results = this.getObservedResult(getRestaurantsForList);
        assertThat(results, notNullValue());
        assert(results.size() == 1);
        assert(results.get(0).getTimeInfo().equals(TimeInfo.DEFAULT_TIME_INFO));
    }

    @Test
    public void restaurantsShouldHaveDistanceInfoWhenAvailable() {
        // Fill repository with list of restaurants
        List<Restaurant> restaurantsToSetInRepository = this.createListOfRestaurants(
                1,
                true,
                true);
        // Use case under test
        GetRestaurantsForList getRestaurantsForList = this.createGetRestaurantsForList(
                LocalTime.of(12, 0),
                1,
                restaurantsToSetInRepository
        );
        // TEST
        List<RestaurantVO> results = this.getObservedResult(getRestaurantsForList);
        assertThat(results, notNullValue());
        assert(results.size() == 1);
        assert(results.get(0).getDistanceInfo() == 100L);
    }

    @Test
    public void restaurantsShouldNotHaveDistanceInfoWhenNotAvailable() {
        // make a list to fill repository
        List<Restaurant> restaurantsToSetInRepository = this.createListOfRestaurants(
                1,
                false,
                false
        );
        // Use case under test
        GetRestaurantsForList getRestaurantsForList = this.createGetRestaurantsForList(
                LocalTime.of(12, 0),
                1,
                restaurantsToSetInRepository
        );
        // TEST
        List<RestaurantVO> results = this.getObservedResult(getRestaurantsForList);
        assertThat(results, notNullValue());
        assert(results.size() == 1);
        assert(results.get(0).getDistanceInfo() == null);
    }

    private List<RestaurantVO> getObservedResult(GetRestaurantsForList getRestaurantsForList) {
        Observable<List<RestaurantVO>> observableRestaurants = getRestaurantsForList
                .getRestaurantsWithSelections(new Geolocation(1111.1, 1111.2), 1000);
        List<RestaurantVO> results = new ArrayList<>();
        observableRestaurants.subscribe(results::addAll);
        return results;
    }

    private GetRestaurantsForList createGetRestaurantsForList(LocalTime now, int today, List<Restaurant> restaurantsToSetInRepository) {
        // Prepare repository
        InMemoryRestaurantGateway restaurantQuery = new InMemoryRestaurantGateway();
        restaurantQuery.setRestaurants(restaurantsToSetInRepository);
        // Prepare use case under test
        GetRestaurantsForList getRestaurantsForList = new GetRestaurantsForList(
                restaurantQuery,
                new DeterministicTimeProvider(now),
                new DeterministicDateProvider(1),
                new InMemoryDistanceRepository(Observable.just(100L)),
                new InMemorySelectionGateway(),
                new InMemoryHistoricOfSelectionsGateway()
        );
        return getRestaurantsForList;
    }


    private List<Restaurant> createListOfRestaurants(
            int numberOfRestaurants,
            boolean hasGeolocation,
            boolean isOpenOnMonday0800_1830
    ) {
        List<Restaurant> restaurants = new ArrayList<>();
        // Prepare data to set in repository
        while(restaurants.size() < numberOfRestaurants) {
            Restaurant restaurant = new Restaurant("Restaurant de la Pointe", "97 rue Langevin");
            if(hasGeolocation) {
                restaurant.setGeolocation(new Geolocation(1111.111, 1111.12222));
            }
            if(isOpenOnMonday0800_1830) {
                // Is open on Monday 8:00 - 18:30
                Map<Integer, Map<String, LocalTime>> planning = new HashMap<>();
                Map<String, LocalTime> dayTimes = new HashMap<>();
                dayTimes.put("open", LocalTime.of(8, 0));
                dayTimes.put("close", LocalTime.of(18, 30));
                planning.put(1, dayTimes);
                restaurant.setPlanning(planning);
            }
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    // Tests selections
    @Test
    public void shouldShowSelectionWhenRestaurantHasSelection() {
        InMemorySelectionGateway currentSelectionsRepository = new InMemorySelectionGateway();
        Restaurant selectedRestaurant = new Restaurant("Au Blé", "Allée des champs");
        selectedRestaurant.setId("uuid1");

        currentSelectionsRepository.setSelections(Arrays.asList(
                new Selection(
                        selectedRestaurant,
                        new Workmate("Janie")
                ),
                new Selection(
                        selectedRestaurant,
                        new Workmate("Cyril")
                )
        ));

        Restaurant restaurant = new Restaurant("Au Blé", "Allée des champs");
        restaurant.setId("uuid1");
        InMemoryRestaurantGateway restaurantRepository = new InMemoryRestaurantGateway();
        restaurantRepository.setRestaurants(Arrays.asList(restaurant));
        GetRestaurantsForList getRestaurantsForList = new GetRestaurantsForList(
                restaurantRepository,
                new DeterministicTimeProvider(LocalTime.now()), new DeterministicDateProvider(1),
                new InMemoryDistanceRepository(Observable.just(10L)),
                currentSelectionsRepository,
                new InMemoryHistoricOfSelectionsGateway()
        );

        Observable<List<RestaurantVO>> observableRestaurants = getRestaurantsForList.getRestaurantsWithSelections(
                new Geolocation(111.111, 122.22), 1000
        );
        List<RestaurantVO> results = new ArrayList<>();
        observableRestaurants.subscribe(results::addAll);
        assert(results.get(0).getSelectionCountInfo() == 2);
    }
}
