package com.android.go4lunch.usecases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.android.go4lunch.in_memory_gateways.InMemoryVisitorGateway;
import com.android.go4lunch.in_memory_gateways.InMemorySessionGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IsTheCurrentSelectionUseCaseTest {

    @Test
    public void theCurrentSelectionIsTheRestaurantThatIsSelectedByTheCurrentSession() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        visitorGateway.setSelections(Arrays.asList(new Selection("restaurant1", "workmate1")));
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate workmateSession = new Workmate("Janie");
        workmateSession.setId("workmate1");
        sessionGateway.setSession(workmateSession);

        List<Boolean> isTheCurrentSelectionResults = new ArrayList<>();
        new IsTheCurrentSelectionUseCase(
                visitorGateway,
                sessionGateway
        ).handle("restaurant1").subscribe(isTheCurrentSelectionResults::add);

        assertTrue(isTheCurrentSelectionResults.get(0));
    }

    @Test
    public void noCurrentSelectionWhenTheCurrentSessionHasNotSelectedAnyRestaurant() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        visitorGateway.setSelections(Arrays.asList(new Selection("restaurant2", "workmate2")));
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate workmateSession = new Workmate("Janie");
        workmateSession.setId("workmate1");
        sessionGateway.setSession(workmateSession);

        List<Boolean> isTheCurrentSelectionResults = new ArrayList<>();
        new IsTheCurrentSelectionUseCase(
                visitorGateway,
                sessionGateway
        ).handle("restaurant2").subscribe(isTheCurrentSelectionResults::add);
        assertFalse(isTheCurrentSelectionResults.get(0));
    }
}
