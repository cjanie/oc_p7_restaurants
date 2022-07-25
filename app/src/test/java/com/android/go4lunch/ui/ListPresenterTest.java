package com.android.go4lunch.ui;

import static org.junit.Assert.assertEquals;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.entities.Like;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.usecases.GetDistanceFromMyPositionToRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.GetNumberOfLikesPerRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.GetRestaurantsNearbyUseCase;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.in_memory_gateways.InMemoryDistanceGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryLikeGateway;
import com.android.go4lunch.ui.presenters.RestaurantListPresenter;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class ListPresenterTest {

    private List<RestaurantValueObject> getSUTResults(GetRestaurantsNearbyUseCase useCase) {
        Observable<List<RestaurantValueObject>> observableRestaurants = useCase
                .handle(1111.1, 1111.2, 1000);
        List<RestaurantValueObject> results = new ArrayList<>();
        observableRestaurants.subscribe(results::addAll);
        return results;
    }

    @Test
    public void restaurantCanHaveLikes() {
        Restaurant restaurant1 = new Restaurant("Chez Jojo", "adresse");
        restaurant1.setId("resto1");
        restaurant1.setGeolocation(new Geolocation(1.8, 1.9));

        RestaurantValueObject restaurantVO1 = new RestaurantValueObject(restaurant1);

        Observable<List<RestaurantValueObject>> restaurants = Observable.just(Arrays.asList(restaurantVO1));

        Like like1Resto1 = new Like("resto1", "workmate1");
        Like like2Resto1 = new Like("resto1", "workmate2");

        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        likeGateway.setLikes(Arrays.asList(like1Resto1, like2Resto1));

        GetNumberOfLikesPerRestaurantUseCase getLikesPerRestaurantUsecase = new GetNumberOfLikesPerRestaurantUseCase(likeGateway);

        GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase = new GetDistanceFromMyPositionToRestaurantUseCase(new InMemoryDistanceGateway(Observable.just(1L)));

        RestaurantListPresenter presenter = new RestaurantListPresenter(getLikesPerRestaurantUsecase, distanceUseCase);
        Observable<List<RestaurantValueObject>> updated = presenter.updateRestaurantsWithLikesCount(restaurants);

        List<RestaurantValueObject> results = new ArrayList<>();
        updated.subscribe(results::addAll);
        assertEquals(2, results.get(0).getNumberOfStarts());
    }

    @Test
    public void restaurantNotLiked() {
        Restaurant restaurant1 = new Restaurant("Chez Jojo", "adresse");
        restaurant1.setId("resto1");
        restaurant1.setGeolocation(new Geolocation(1.1, 1.3));
        RestaurantValueObject restaurantVO1 = new RestaurantValueObject(restaurant1);

        Observable<List<RestaurantValueObject>> restaurants = Observable.just(Arrays.asList(restaurantVO1));

        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        GetNumberOfLikesPerRestaurantUseCase likeUseCase = new GetNumberOfLikesPerRestaurantUseCase(likeGateway);
        GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase = new GetDistanceFromMyPositionToRestaurantUseCase(new InMemoryDistanceGateway(Observable.just(1L)));
        RestaurantListPresenter presenter = new RestaurantListPresenter(likeUseCase, distanceUseCase);
        Observable<List<RestaurantValueObject>> updated = presenter.updateRestaurantsWithLikesCount(restaurants);

        List<RestaurantValueObject> results = new ArrayList<>();
        updated.subscribe(results::addAll);
        assertEquals(0, results.get(0).getNumberOfStarts());
    }

    @Test
    public void starsNotMoreThanThree() {
        Restaurant restaurant1 = new Restaurant("Chez Jojo", "adresse");
        restaurant1.setId("resto1");
        restaurant1.setGeolocation(new Geolocation(1.1, 1.1));

        RestaurantValueObject restaurantVO1 = new RestaurantValueObject(restaurant1);

        Observable<List<RestaurantValueObject>> restaurants = Observable.just(Arrays.asList(restaurantVO1));

        Like like1Resto1 = new Like("resto1", "workmate1");
        Like like2Resto1 = new Like("resto1", "workmate2");
        Like like3Resto1 = new Like("resto1", "workmate3");
        Like like4Resto1 = new Like("resto1", "workmate4");

        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        likeGateway.setLikes(Arrays.asList(like1Resto1, like2Resto1, like3Resto1, like4Resto1));
        GetNumberOfLikesPerRestaurantUseCase likeUseCase = new GetNumberOfLikesPerRestaurantUseCase(likeGateway);
        GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase = new GetDistanceFromMyPositionToRestaurantUseCase(new InMemoryDistanceGateway(Observable.just(1L)));

        RestaurantListPresenter presenter = new RestaurantListPresenter(likeUseCase, distanceUseCase);
        Observable<List<RestaurantValueObject>> updated = presenter.updateRestaurantsWithLikesCount(restaurants);

        List<RestaurantValueObject> results = new ArrayList<>();
        updated.subscribe(results::addAll);
        assertEquals(3, results.get(0).getNumberOfStarts());

    }

    @Test
    public void returnRestaurantsWithDistance() {
        Restaurant restaurant1 = new Restaurant("Chez Lol", "allée");
        restaurant1.setId("1");
        RestaurantValueObject restaurantVO1 = new RestaurantValueObject(restaurant1);

        Observable<List<RestaurantValueObject>> restaurantsObservable = Observable.just(Arrays.asList(restaurantVO1));

        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        GetNumberOfLikesPerRestaurantUseCase likeUseCase = new GetNumberOfLikesPerRestaurantUseCase(likeGateway);

        InMemoryDistanceGateway distanceGateway = new InMemoryDistanceGateway(Observable.just(1L));
        GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase = new GetDistanceFromMyPositionToRestaurantUseCase(distanceGateway);
        RestaurantListPresenter presenter = new RestaurantListPresenter(likeUseCase, distanceUseCase);

        List<RestaurantValueObject> results = new ArrayList<>();
        presenter.updateRestaurantsWithDistance(restaurantsObservable, 1.1, 1.2).subscribe(results::addAll);
        assert(results.get(0).getDistance() == 1L);
    }

}
