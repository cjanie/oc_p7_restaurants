package com.android.go4lunch.businesslogic.usecases;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.in_memory_gateways.InMemorySessionGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryVisitorGateway;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetMyLunchUseCaseTest {

    @Test
    public void returnsTheSelectedRestaurantWhenSessionHasSelectedOne() {
        Workmate session = new Workmate("Janie");
        session.setId("sessionId");
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        sessionGateway.setSession(session);

        Selection selection1 = new Selection("resto1", "sessionId");
        Selection selection2 = new Selection("resto2", "workmate2");
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        visitorGateway.setSelections(Arrays.asList(selection1, selection2));

        List<Restaurant> myLunchResults = new ArrayList<>();
        new GetMyLunchUseCase(sessionGateway, visitorGateway).handle().subscribe(myLunchResults::add);
        assertEquals("resto1", myLunchResults.get(0).getId());
    }

    @Test
    public void returnsNothingWhenSessionHasNoSelectedRestaurant() {
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("sessionId");
        sessionGateway.setSession(session);

        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        visitorGateway.setSelections(new ArrayList<>());

        List<Restaurant> myLunchResults = new ArrayList<>();
        new GetMyLunchUseCase(sessionGateway, visitorGateway).handle().subscribe(myLunchResults::add);
        assert(myLunchResults.isEmpty());
    }
}
