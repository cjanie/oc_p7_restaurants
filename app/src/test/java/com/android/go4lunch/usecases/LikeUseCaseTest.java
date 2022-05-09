package com.android.go4lunch.usecases;

import static org.junit.Assert.assertEquals;

import com.android.go4lunch.gateways.LikeGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryLikeGateway;
import com.android.go4lunch.models.Like;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

class LikeUseCase {

    private LikeGateway likeGateway;

    public LikeUseCase(LikeGateway likeGateway) {
        this.likeGateway = likeGateway;
    }

    public void handle(String restaurantId, String workmateId) {
        List<Like> likesResults = new ArrayList<>();
        this.likeGateway.getLikes().subscribe(likesResults::addAll);
        boolean found = false;
        for(Like like: likesResults) {
            if(like.getRestaurantId().equals(restaurantId) && like.getWorkmateId().equals(workmateId)) {
                found = true;
                break;
            }
        }
        if(!found)
            this.likeGateway.add(new Like(restaurantId, workmateId));
    }


}

public class LikeUseCaseTest {

    @Test
    public void toLikeIncrementsTheNumberOfLikes() throws NotFoundException {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate1");
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate2");
        List<Like> likesResult = new ArrayList<>();
        likeGateway.getLikes().subscribe(likesResult::addAll);

        assertEquals(2, likesResult.size());
    }

    @Test
    public void aRestaurantCannotHaveManyLikesFromTheSameWorkmate() throws NotFoundException {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate1");
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate1");
        List<Integer> numberOflikesPerRestaurantResults = new ArrayList<>();
        new GetNumberOfLikesPerRestaurantUseCase(likeGateway).handle("restaurant1")
                .subscribe(numberOflikesPerRestaurantResults::add);
        assert(numberOflikesPerRestaurantResults.get(0) == 1);
    }
}
