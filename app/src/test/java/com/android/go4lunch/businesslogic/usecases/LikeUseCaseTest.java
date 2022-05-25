package com.android.go4lunch.businesslogic.usecases;

import static org.junit.Assert.assertEquals;

import com.android.go4lunch.in_memory_gateways.InMemoryLikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LikeUseCaseTest {

    @Test
    public void toLikeIncrementsTheNumberOfLikes() {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate1");
        new LikeUseCase(likeGateway).handle("restaurant2", "workmate2");
        List<Like> likesResult = new ArrayList<>();
        likeGateway.getLikes().subscribe(likesResult::addAll);

        assertEquals(2, likesResult.size());
    }

    @Test
    public void aRestaurantCanBeLikedByManyWorkmates() {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate1");
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate2");
        List<Like> likesResult = new ArrayList<>();
        likeGateway.getLikes().subscribe(likesResult::addAll);

        assertEquals(2, likesResult.size());
    }
    @Test
    public void aRestaurantCannotHaveManyLikesFromTheSameWorkmate() {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate1");
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate1");
        List<Integer> numberOflikesPerRestaurantResults = new ArrayList<>();
        new GetNumberOfLikesPerRestaurantUseCase(likeGateway).handle("restaurant1")
                .subscribe(numberOflikesPerRestaurantResults::add);
        assert(numberOflikesPerRestaurantResults.get(0) == 1);
    }

    @Test
    public void oneWorkmateCanLikeManyRestaurants() {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate1");
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate2");
        List<Like> likesResults = new ArrayList<>();
        likeGateway.getLikes().subscribe(likesResults::addAll);
        assert(likesResults.size() == 2);
    }
}
