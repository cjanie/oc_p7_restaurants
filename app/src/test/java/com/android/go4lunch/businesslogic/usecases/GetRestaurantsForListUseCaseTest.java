package com.android.go4lunch.businesslogic.usecases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.android.go4lunch.businesslogic.entities.Like;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.in_memory_gateways.InMemoryDistanceGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryLikeGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryRestaurantGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryVisitorGateway;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForListUseCaseTest {


    private GetRestaurantsForListUseCase createGetRestaurantsForList(
            int availableRestaurantsSize) {
        // Fill repository with available restaurants
        InMemoryRestaurantGateway restaurantGateway = new InMemoryRestaurantGateway();
        List<Restaurant> availableRestaurants = new ArrayList<>();
        while(availableRestaurants.size() < availableRestaurantsSize) {
            Restaurant restaurant = new Restaurant("Restaurant de la Pointe", "97 rue Langevin");
           availableRestaurants.add(restaurant);
        }
        restaurantGateway.setRestaurants(availableRestaurants);
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        InMemoryDistanceGateway distanceGateway = new InMemoryDistanceGateway(Observable.just(1L));

        return new GetRestaurantsForListUseCase(
                restaurantGateway,
                visitorGateway,
                likeGateway,
                distanceGateway
        );
    }

    private GetRestaurantsForListUseCase createUseCaseWithAvailableSelections(List<Restaurant> availableRestaurants, List<Selection> availableSelections) {
        InMemoryRestaurantGateway restaurantGateway = new InMemoryRestaurantGateway();
        restaurantGateway.setRestaurants(availableRestaurants);
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        visitorGateway.setSelections(availableSelections);
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        InMemoryDistanceGateway distanceGateway = new InMemoryDistanceGateway(Observable.just(1L));

        return new GetRestaurantsForListUseCase(
                restaurantGateway,
                visitorGateway,
                likeGateway,
                distanceGateway
        );
    }

    private GetRestaurantsForListUseCase createUseCaseWithAvailableLikes(List<Restaurant> availableRestaurants, List<Like> availableLikes) {
        InMemoryRestaurantGateway restaurantGateway = new InMemoryRestaurantGateway();
        restaurantGateway.setRestaurants(availableRestaurants);
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        likeGateway.setLikes(availableLikes);
        InMemoryDistanceGateway distanceGateway = new InMemoryDistanceGateway(Observable.just(1L));

        return new GetRestaurantsForListUseCase(
                restaurantGateway,
                visitorGateway,
                likeGateway,
                distanceGateway
        );
    }

    private List<RestaurantValueObject> getObservedResult(GetRestaurantsForListUseCase getRestaurantsForListUseCase) {
        Observable<List<RestaurantValueObject>> observableRestaurants = getRestaurantsForListUseCase
                .handle(1111.1, 1111.2, 1000);
        List<RestaurantValueObject> results = new ArrayList<>();
        observableRestaurants.subscribe(results::addAll);
        return results;
    }

    @Test
    public void returnsRestaurantsWhenThereAreSomeAvailable() {

        // SUT with 2 restaurants available
        GetRestaurantsForListUseCase getRestaurantsForListUseCase = this.createGetRestaurantsForList(
                2
        );

        assert(this.getObservedResult(getRestaurantsForListUseCase).size() == 2);
    }

    @Test
    public void listShouldBeEmptyWhenNoRestaurantIsAvailable() {

        // SUT without any restaurant available
        GetRestaurantsForListUseCase getRestaurantsForListUseCase = this.createGetRestaurantsForList(
                0
        );

        assert(this.getObservedResult(getRestaurantsForListUseCase).isEmpty());
    }

    @Test
    public void restaurantCanHaveSelections() {
        Restaurant restaurant1 = new Restaurant("Chez Jojo");
        restaurant1.setId("resto1");
        Restaurant restaurant2 = new Restaurant("Chez Janvier");
        restaurant2.setId("resto2");

        Selection selection1Resto2 = new Selection("resto2", "workmate1");
        Selection selection2Resto2 = new Selection("resto2", "workmate2");

        // SUT
        GetRestaurantsForListUseCase getRestaurantsForListUseCase = this.createUseCaseWithAvailableSelections(
                Arrays.asList(restaurant1, restaurant2),
                Arrays.asList(selection1Resto2, selection2Resto2)
        );
        assertEquals(0, this.getObservedResult(getRestaurantsForListUseCase).get(0).getVisitorsCount());
        assertEquals(2, this.getObservedResult(getRestaurantsForListUseCase).get(1).getVisitorsCount());
    }

    @Test
    public void restaurantCanHaveLikes() {
        Restaurant restaurant1 = new Restaurant("Chez Jojo");
        restaurant1.setId("resto1");

        Like like1Resto1 = new Like("resto1", "workmate1");
        Like like2Resto1 = new Like("resto1", "workmate2");

        GetRestaurantsForListUseCase getRestaurantsForListUseCase = this.createUseCaseWithAvailableLikes(
                Arrays.asList(restaurant1),
                Arrays.asList(like1Resto1, like2Resto1)
        );
        assertEquals(2, this.getObservedResult(getRestaurantsForListUseCase).get(0).getNumberOfStarts());
    }

    @Test
    public void restaurantNotLiked() {
        Restaurant restaurant1 = new Restaurant("Chez Jojo");
        restaurant1.setId("resto1");
        GetRestaurantsForListUseCase getRestaurantsForListUseCase = this.createUseCaseWithAvailableLikes(
                Arrays.asList(restaurant1),
                Arrays.asList()
        );
        assertEquals(0, this.getObservedResult(getRestaurantsForListUseCase).get(0).getNumberOfStarts());
    }

    @Test
    public void starsNotMoreThanThree() {
        Restaurant restaurant1 = new Restaurant("Chez Jojo");
        restaurant1.setId("resto1");

        Like like1Resto1 = new Like("resto1", "workmate1");
        Like like2Resto1 = new Like("resto1", "workmate2");
        Like like3Resto1 = new Like("resto1", "workmate3");
        Like like4Resto1 = new Like("resto1", "workmate4");

        GetRestaurantsForListUseCase getRestaurantsForListUseCase = this.createUseCaseWithAvailableLikes(
                Arrays.asList(restaurant1),
                Arrays.asList(like1Resto1, like2Resto1, like3Resto1, like4Resto1)
        );
        assertEquals(3, this.getObservedResult(getRestaurantsForListUseCase).get(0).getNumberOfStarts());

    }

}
