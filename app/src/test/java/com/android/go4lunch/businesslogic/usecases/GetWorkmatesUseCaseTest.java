package com.android.go4lunch.businesslogic.usecases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.usecases.workmate.GetWorkmatesUseCase;
import com.android.go4lunch.businesslogic.valueobjects.WorkmateValueObject;
import com.android.go4lunch.in_memory_gateways.InMemorySessionGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryVisitorGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryWorkmateGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetWorkmatesUseCaseTest {

    @Test
    public void listShouldNotContainTheWorkmateOfTheCurrentSession() {
        // Workmates
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        Workmate janie = new Workmate("Janie");
        janie.setId("1");
        Workmate cyril = new Workmate("Cyril");
        cyril.setId("2");
        workmateGateway.setWorkmates(Arrays.asList(
                janie,
                cyril
        ));

        // Session
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Cyril");
        session.setId("2");
        sessionGateway.setSession(session);

        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        // SUT
        List<WorkmateValueObject> workmatesResults = new ArrayList<>();
        new GetWorkmatesUseCase(
                workmateGateway,
                sessionGateway,
                visitorGateway
        ).handle()
                .subscribe(workmates -> {
                    workmatesResults.addAll(workmates);
                });
        assertEquals(1, workmatesResults.size());
    }

    @Test
    public void listShouldBeEmptyWhenNoAvailableWorkmate() {
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        // dont set any workmate in the repository
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        // SUT
        List<WorkmateValueObject> workmatesResults = new ArrayList<>();
        new GetWorkmatesUseCase(
                workmateGateway,
                sessionGateway,
                visitorGateway
                ).handle()
                .subscribe(workmates -> {
                    workmatesResults.addAll(workmates);
                });
        assert(workmatesResults.isEmpty());
    }

    @Test
    public void workmateCanSelectARestaurant() {
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        Workmate janie = new Workmate("Janie");
        janie.setId("workmate1");
        Workmate cyril = new Workmate("cyril");
        cyril.setId("workmate2");
        workmateGateway.setWorkmates(Arrays.asList(janie, cyril));

        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        sessionGateway.setSession(cyril);

        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        Selection selection = new Selection("restaurant1", "workmate1");
        visitorGateway.setSelections(Arrays.asList(selection));

        List<WorkmateValueObject> workmatesResults = new ArrayList<>();
        new GetWorkmatesUseCase(
                workmateGateway,
                sessionGateway,
                visitorGateway
        ).handle()
                .subscribe(workmates -> {
                    workmatesResults.addAll(workmates);
                });
        assertNotNull(workmatesResults.get(0).getSelection());
        assertEquals("restaurant1", workmatesResults.get(0).getSelection().getId());
    }

    @Test
    public void workmateCanHaveNoSelection() {
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        Workmate janie = new Workmate("Janie");
        janie.setId("workmate1");
        Workmate cyril = new Workmate("cyril");
        cyril.setId("workmate2");
        Workmate titi = new Workmate("Titi");
        titi.setId("workmate3");

        workmateGateway.setWorkmates(Arrays.asList(janie, cyril, titi));

        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        sessionGateway.setSession(cyril);

        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        // No selection to set in the gateway

        // SUT
        List<WorkmateValueObject> workmatesResults = new ArrayList<>();
        new GetWorkmatesUseCase(workmateGateway, sessionGateway, visitorGateway).handle().subscribe(
                workmates -> workmatesResults.addAll(workmates)
        );

        assertNull(workmatesResults.get(1).getSelection());

    }

    @Test
    public void manyWorkmatesCanHaveASelectedRestaurant() {
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        Workmate janie = new Workmate("Janie");
        janie.setId("workmate1");
        Workmate cyril = new Workmate("cyril");
        cyril.setId("workmate2");
        Workmate titi = new Workmate("Titi");
        titi.setId("workmate3");

        workmateGateway.setWorkmates(Arrays.asList(janie, cyril, titi));

        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        sessionGateway.setSession(cyril);

        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        Selection selection1 = new Selection("restaurant1", "workmate1");
        Selection selection2 = new Selection("restaurant1", "workmate3");
        visitorGateway.setSelections(Arrays.asList(selection1, selection2));

        List<WorkmateValueObject> workmatesResults = new ArrayList<>();
        new GetWorkmatesUseCase(workmateGateway, sessionGateway, visitorGateway).handle()
                .subscribe(workmates -> workmatesResults.addAll(workmates));
        assertEquals(2, workmatesResults.size());
        assertEquals("restaurant1", workmatesResults.get(1).getSelection().getId());

    }
}
