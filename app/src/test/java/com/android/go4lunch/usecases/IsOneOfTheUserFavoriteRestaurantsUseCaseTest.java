package com.android.go4lunch.usecases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.android.go4lunch.in_memory_gateways.InMemoryLikeGateway;
import com.android.go4lunch.in_memory_gateways.InMemorySessionGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.exceptions.NoWorkmateForSessionException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class IsOneOfTheUserFavoriteRestaurantsUseCaseTest {

    @Test
    public void aRestaurantIsOneOfFavoritesWhenTheWorkmateOfTheSessionHasLikedIt() throws NoWorkmateForSessionException {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        new LikeUseCase(likeGateway).handle("restaurant1", "workmate1");
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate workmateForSession = new Workmate("Janie");
        workmateForSession.setId("workmate1");
        sessionGateway.setSession(workmateForSession);
        List<Boolean> isFavoriteResults = new ArrayList<>();
        new IsOneOfTheUserFavoriteRestaurantsUseCase(
                likeGateway,
                sessionGateway
        ).handle("restaurant1").subscribe(isFavoriteResults::add);
        assertTrue(isFavoriteResults.get(0));
    }

    @Test
    public void aRestaurantIsNotAFavoriteWhenTheWorkmateOfTheSessionHasNosLikedIt() throws NoWorkmateForSessionException {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate workmateForSession = new Workmate("Janie");
        workmateForSession.setId("workmate1");
        sessionGateway.setSession(workmateForSession);
        List<Boolean> isFavoriteResults = new ArrayList<>();
        new IsOneOfTheUserFavoriteRestaurantsUseCase(
                likeGateway,
                sessionGateway
        ).handle("restaurant1").subscribe(isFavoriteResults::add);
        assertFalse(isFavoriteResults.get(0));
    }

    @Test(expected = NoWorkmateForSessionException.class)
    public void errorWhenThereIsNoWorkmateForSession() throws NoWorkmateForSessionException {
        InMemoryLikeGateway likeGateway = new InMemoryLikeGateway();
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        new IsOneOfTheUserFavoriteRestaurantsUseCase(likeGateway, sessionGateway).handle("restaurant1");
    }


}
