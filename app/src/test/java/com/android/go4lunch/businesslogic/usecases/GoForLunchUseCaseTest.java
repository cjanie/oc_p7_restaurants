package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.in_memory_gateways.InMemorySessionGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryVisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.entities.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GoForLunchUseCaseTest {


    @Test
    public void likeForLunchIncrementsVisitors() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("2", "2");
        selections.add(selection);
        visitorGateway.setSelections(selections);
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("1");
        sessionGateway.setSession(session);

        GoForLunchUseCase goForLunchUseCase = new GoForLunchUseCase(
                visitorGateway, sessionGateway);

        // LAUNCH LIKE
        goForLunchUseCase.handle("1", "resto1").subscribe();

        List<Selection> savedSelections = new ArrayList<>();
        visitorGateway.getSelections().subscribe(savedSelections::addAll);

        assert(savedSelections.size() == 2);
    }

    @Test
    public void cancelSelectionDecrementsVisitors() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("1");
        sessionGateway.setSession(session);
        GoForLunchUseCase goForLunchUseCase = new GoForLunchUseCase(visitorGateway, sessionGateway);
        // Increments
        goForLunchUseCase.handle("1", "resto1");
        // Decrements
        goForLunchUseCase.handle("1", "resto1");

        List<Selection> savedSelections = new ArrayList<>();
        visitorGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 0);
    }

    @Test
    public void cancelSelectionDeletesTheSelectionOfTheUser() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        List<Selection> selections = new ArrayList<>();
        Workmate cyril = new Workmate("Cyril");
        cyril.setId("1");
        Selection selection = new Selection("1", "1");
        selections.add(selection);
        visitorGateway.setSelections(selections);
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Cyril");
        session.setId("2");
        sessionGateway.setSession(session);
        GoForLunchUseCase goForLunchUseCase = new GoForLunchUseCase(
                visitorGateway, sessionGateway
        );
        goForLunchUseCase.handle("2","resto2");
        goForLunchUseCase.handle("2", "resto2");
        List<Selection> savedSelections = new ArrayList<>();
        visitorGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 1);
        assert(savedSelections.get(0).getWorkmateId().equals("1"));
    }

    @Test
    public void workmateCannotHaveManySelections() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("2", "2");
        selections.add(selection);
        visitorGateway.setSelections(selections);
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("2");
        sessionGateway.setSession(session);
        GoForLunchUseCase goForLunchUseCase = new GoForLunchUseCase(
                visitorGateway, sessionGateway
        );

        // LAUNCH LIKE
        goForLunchUseCase.handle("1", "resto2");

        List<Selection> savedSelections = new ArrayList<>();
        visitorGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 1);
    }

}
