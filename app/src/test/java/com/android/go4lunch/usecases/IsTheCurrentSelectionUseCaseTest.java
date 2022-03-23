package com.android.go4lunch.usecases;

import com.android.go4lunch.in_memory_repositories.InMemoryVisitorsGateway;
import com.android.go4lunch.models.Selection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsTheCurrentSelectionUseCaseTest {

    @Test
    public void informsIfRestaurantIsSelectedByTheCurrentSession() {
        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();
        IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase = new IsTheCurrentSelectionUseCase(visitorsGateway);

        List<Boolean> isThecurrentSelectionResults = new ArrayList<>();
        isTheCurrentSelectionUseCase.handle(
                "1",
                "1"
        ).subscribe(isThecurrentSelectionResults::add);
        assertFalse(isThecurrentSelectionResults.get(0));
    }

    @Test
    public void isTheCurrentSelectionWhenRestaurantIsSelectedByTheCurrentSession() {
        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();
        visitorsGateway.setSelections(Arrays.asList(new Selection(
                "1",
                "1"
        )));
        IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase = new IsTheCurrentSelectionUseCase(visitorsGateway);
        List<Boolean> isTheCurrentSelectionResults = new ArrayList<>();
        isTheCurrentSelectionUseCase.handle(
                "1",
                "1"
        ).subscribe(isTheCurrentSelectionResults::add);
        assertTrue(isTheCurrentSelectionResults.get(0));
    }
}
