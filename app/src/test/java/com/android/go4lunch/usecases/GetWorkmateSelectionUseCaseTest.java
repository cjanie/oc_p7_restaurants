package com.android.go4lunch.usecases;

import com.android.go4lunch.in_memory_repositories.InMemoryVisitorsGateway;
import com.android.go4lunch.models.Selection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNull;

public class GetWorkmateSelectionUseCaseTest {

    @Test
    public void retrievesWorkmateSelectionWhenThereIsOne() {
        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();
        Selection selection1 = new Selection(
                "1",
                "Chez Jojo",
                "1",
                "Janie"
                );
        Selection selection2 = new Selection(
                "2", "Chez Jojo",
                "2", "Cyril"
        );
        visitorsGateway.setSelections(Arrays.asList(selection1, selection2));
        GetWorkmateSelectionUseCase getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(visitorsGateway);
        List<Selection> selectionResults = new ArrayList<>();
        getWorkmateSelectionUseCase.handle("2").subscribe(selectionResults::add);
        assert(selectionResults.get(0).getRestaurantId().equals("2") && selectionResults.get(0).getWorkmateId().equals("2"));
    }

    @Test
    public void nothingWhenWormateHasNoSelection() {
        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();
        GetWorkmateSelectionUseCase getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(visitorsGateway);
        assertNull(getWorkmateSelectionUseCase.handle("1"));
    }
}