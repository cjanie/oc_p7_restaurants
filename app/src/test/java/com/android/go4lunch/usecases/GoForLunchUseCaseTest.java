package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemoryVisitorGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GoForLunchUseCaseTest {


    @Test
    public void likeForLunchIncrementsVisitors() {
        InMemoryVisitorGateway inMemoryVisitorsGateway = new InMemoryVisitorGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("2", "2");
        selections.add(selection);
        inMemoryVisitorsGateway.setSelections(selections);

        GoForLunchUseCase goForLunchUseCase = new GoForLunchUseCase(inMemoryVisitorsGateway);

        // LAUNCH LIKE
        goForLunchUseCase.handle("1", "1");

        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);

        assert(savedSelections.size() == 2);
    }

    @Test
    public void cancelSelectionDecrementsVisitors() {
        InMemoryVisitorGateway inMemoryVisitorsGateway = new InMemoryVisitorGateway();
        GoForLunchUseCase goForLunchUseCase = new GoForLunchUseCase(inMemoryVisitorsGateway);
        // Increments
        goForLunchUseCase.handle("1",  "1");
        // Decrements
        goForLunchUseCase.handle("1", "1");

        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 0);
    }

    @Test
    public void cancelSelectionDeletesTheSelectionOfTheUser() {
        InMemoryVisitorGateway inMemoryVisitorsGateway = new InMemoryVisitorGateway();
        List<Selection> selections = new ArrayList<>();
        Workmate cyril = new Workmate("Cyril");
        cyril.setId("1");
        Selection selection = new Selection("1", "1");
        selections.add(selection);
        inMemoryVisitorsGateway.setSelections(selections);

        GoForLunchUseCase goForLunchUseCase = new GoForLunchUseCase(
                inMemoryVisitorsGateway
        );
        goForLunchUseCase.handle("2", "2");
        goForLunchUseCase.handle("2", "2");
        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 1);
        assert(savedSelections.get(0).getWorkmateId().equals("1"));
    }

    @Test
    public void workmateCannotHaveManySelections() {
        InMemoryVisitorGateway inMemoryVisitorsGateway = new InMemoryVisitorGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("2", "2");
        selections.add(selection);
        inMemoryVisitorsGateway.setSelections(selections);

        GoForLunchUseCase goForLunchUseCase = new GoForLunchUseCase(inMemoryVisitorsGateway);

        // LAUNCH LIKE
        goForLunchUseCase.handle("1", "2");

        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 1);
    }

}
