package com.android.go4lunch.businesslogic.usecases;

import static org.junit.Assert.assertEquals;

import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.in_memory_gateways.InMemoryLikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;
import com.android.go4lunch.in_memory_gateways.InMemorySessionGateway;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddLikeUseCaseTest {

    @Test
    public void toLikeIncrementsTheNumberOfLikes() {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("workmate1");
        sessionGateway.setSession(session);

        new AddLikeUseCase(likeGateway, sessionGateway).handle("restaurant1").subscribe();
        new AddLikeUseCase(likeGateway, sessionGateway).handle("restaurant2").subscribe();
        List<Like> likesResult = new ArrayList<>();
        likeGateway.getLikes().subscribe(likesResult::addAll);

        assertEquals(2, likesResult.size());
    }

    @Test
    public void aRestaurantCanBeLikedByManyWorkmates() {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        Like like = new Like("restaurant1", "workmate2");
        likeGateway.setLikes(Arrays.asList(like));
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("workmate1");
        sessionGateway.setSession(session);
        new AddLikeUseCase(likeGateway, sessionGateway).handle("restaurant1").subscribe();
        List<Like> likesResult = new ArrayList<>();
        likeGateway.getLikes().subscribe(likesResult::addAll);

        assertEquals(2, likesResult.size());
    }
    @Test
    public void aRestaurantCannotHaveManyLikesFromTheSameWorkmate() {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("workmate1");
        sessionGateway.setSession(session);
        new AddLikeUseCase(likeGateway, sessionGateway).handle("restaurant1").subscribe();
        new AddLikeUseCase(likeGateway, sessionGateway).handle("restaurant1").subscribe();
        List<Like> likesResults = new ArrayList<>();
        likeGateway.getLikes().subscribe(likes -> likesResults.addAll(likes));
        assertEquals(1, likesResults.size());
    }

    @Test
    public void oneWorkmateCanLikeManyRestaurants() {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("workmate1");
        sessionGateway.setSession(session);
        new AddLikeUseCase(likeGateway, sessionGateway).handle("restaurant1").subscribe();
        new AddLikeUseCase(likeGateway, sessionGateway).handle("restaurant2").subscribe();
        List<Like> likesResults = new ArrayList<>();
        likeGateway.getLikes().subscribe(likesResults::addAll);
        assert(likesResults.size() == 2);
    }
}
