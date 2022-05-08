package com.android.go4lunch.usecases;

import static org.junit.Assert.assertEquals;

import com.android.go4lunch.in_memory_gateways.InMemoryLikeGateway;
import com.android.go4lunch.models.Like;

import org.junit.Test;

import java.util.Arrays;

public class GetNumberOfLikesPerRestaurantUseCaseTest {

    @Test
    public void aRestaurantCanBeLikedByManyWorkmates() {
        InMemoryLikeGateway inMemoryLikeGateway = new InMemoryLikeGateway();
        Like like1 = new Like("restaurant1", "workmate1");
        Like like2 = new Like("restaurant1", "workmate2");
        Like like3 = new Like("restaurant2", "workmate1");
        Like like4 = new Like("restaurant2", "workmate2");
        inMemoryLikeGateway.setLikes(Arrays.asList(like1, like2, like3, like4));
        assertEquals(2, new GetNumberOfLikesPerRestaurantUseCase(inMemoryLikeGateway).handle("restaurant2"));
    }

    @Test
    public void aRestaurantWithoutLikeShouldNotBeFound() {
        InMemoryLikeGateway inMemoryLikeGateway = new InMemoryLikeGateway();
        assertEquals(0, new GetNumberOfLikesPerRestaurantUseCase(inMemoryLikeGateway).handle("restaurant1"));
    }
}
