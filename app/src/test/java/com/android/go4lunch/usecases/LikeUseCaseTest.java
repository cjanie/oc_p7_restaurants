package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LikeUseCaseTest {


    @Test
    public void likeForLunchIncrementsVisitors() {
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("2", "2");
        selections.add(selection);
        inMemoryVisitorsGateway.setSelections(selections);

        LikeUseCase likeUseCase = new LikeUseCase(inMemoryVisitorsGateway);

        // LAUNCH LIKE
        likeUseCase.handle("1", "1");

        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);

        assert(savedSelections.size() == 2);
    }

    @Test
    public void cancelSelectionDecrementsVisitors() {
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        LikeUseCase likeUseCase = new LikeUseCase(inMemoryVisitorsGateway);
        // Increments
        likeUseCase.handle("1",  "1");
        // Decrements
        likeUseCase.handle("1", "1");

        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 0);
    }

    @Test
    public void cancelSelectionDeletesTheSelectionOfTheUser() {
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        List<Selection> selections = new ArrayList<>();
        Workmate cyril = new Workmate("Cyril");
        cyril.setId("1");
        Selection selection = new Selection("1", "1");
        selections.add(selection);
        inMemoryVisitorsGateway.setSelections(selections);

        LikeUseCase likeUseCase = new LikeUseCase(
                inMemoryVisitorsGateway
        );
        likeUseCase.handle("2", "2");
        likeUseCase.handle("2", "2");
        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 1);
        assert(savedSelections.get(0).getWorkmateId().equals("1"));
    }

    @Test
    public void workmateCannotHaveManySelections() {
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("2", "2");
        selections.add(selection);
        inMemoryVisitorsGateway.setSelections(selections);

        LikeUseCase likeUseCase = new LikeUseCase(inMemoryVisitorsGateway);

        // LAUNCH LIKE
        likeUseCase.handle("1", "2");

        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 1);
    }

}
