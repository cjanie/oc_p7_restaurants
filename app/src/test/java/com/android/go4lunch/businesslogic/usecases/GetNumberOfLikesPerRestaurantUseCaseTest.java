package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.in_memory_gateways.InMemoryLikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetNumberOfLikesPerRestaurantUseCaseTest {

    @Test
    public void aRestaurantCanBeLikedByManyWorkmates() {
        InMemoryLikeGateway inMemoryLikeGateway = new InMemoryLikeGateway();
        Like like1 = new Like("restaurant1", "workmate1");
        Like like2 = new Like("restaurant1", "workmate2");
        Like like3 = new Like("restaurant2", "workmate1");
        Like like4 = new Like("restaurant2", "workmate2");
        inMemoryLikeGateway.setLikes(Arrays.asList(like1, like2, like3, like4));
        List<Integer> numberOfLikesPerRestaurantResults = new ArrayList<>();
        new GetNumberOfLikesPerRestaurantUseCase(inMemoryLikeGateway).handle("restaurant2")
                .subscribe(numberOfLikesPerRestaurantResults::add);
        assert(numberOfLikesPerRestaurantResults.get(0) == 2);
    }

    @Test
    public void aRestaurantCanHaveNoLike() {
        InMemoryLikeGateway inMemoryLikeGateway = new InMemoryLikeGateway();
        List<Integer> numberOfLikePerRestaurantResults = new ArrayList<>();
        new GetNumberOfLikesPerRestaurantUseCase(inMemoryLikeGateway).handle("restaurant1")
                .subscribe(numberOfLikePerRestaurantResults::add);
        assert(numberOfLikePerRestaurantResults.get(0) == 0);
    }
}
