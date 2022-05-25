package com.android.go4lunch.usecases;

import static org.junit.Assert.assertEquals;

import com.android.go4lunch.in_memory_gateways.InMemorySessionGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryWorkmateGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;

import org.junit.Test;

import java.util.Arrays;

public class GetWorkmatesUseCaseTest {

    @Test
    public void listShouldHave1WorkmateWhenThereIs1Available() {
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        workmateGateway.setWorkmates(Arrays.asList(new Workmate("Janie")));
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        // SUT
        new GetWorkmatesUseCase(workmateGateway, sessionGateway).handle()
                .subscribe(workmates -> {
                    assert(workmates.size() == 1);
                });

    }

    @Test
    public void listShouldHave2WorkmateWhenThereIs2Available() {
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        workmateGateway.setWorkmates(Arrays.asList(
                new Workmate("Janie"),
                new Workmate("Cyril")
        ));
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        new GetWorkmatesUseCase(workmateGateway, sessionGateway).handle()
                .subscribe(workmates -> {
                    assert(workmates.size() == 2);
                });

    }

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
        // SUT
        new GetWorkmatesUseCase(workmateGateway, sessionGateway).handle()
                .subscribe(workmates -> {
                    assertEquals(1, workmates.size());
                });
    }

    @Test
    public void listShouldBeEmptyWhenNoAvailableWorkmate() {
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        // dont set any workmate in the repository
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        // SUT
        new GetWorkmatesUseCase(workmateGateway, sessionGateway).handle()
                .subscribe(workmates -> {
                    assert(workmates.isEmpty());
                });
    }

}
