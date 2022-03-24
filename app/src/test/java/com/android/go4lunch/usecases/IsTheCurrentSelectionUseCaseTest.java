package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.models.Selection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsTheCurrentSelectionUseCaseTest {

    @Test
    public void isNotWhenRestaurantIsNotSelected() {
        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();
        // No visitors in visitors gateway
        IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase = new IsTheCurrentSelectionUseCase(visitorsGateway);

        // SUT is the current selection ?
        List<Boolean> isThecurrentSelectionResults = new ArrayList<>();
        isTheCurrentSelectionUseCase.handle(
                "1",
                "1"
        ).subscribe(isThecurrentSelectionResults::add);

        // Is not
        assertFalse(isThecurrentSelectionResults.get(0));
    }

    @Test
    public void isTheCurrentSelectionWhenRestaurantIsSelectedByTheCurrentSession() {
        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();
        // Set selections in gateway
        visitorsGateway.setSelections(Arrays.asList(new Selection("1", "1")));

        // SUT is the current selection ?
        IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase = new IsTheCurrentSelectionUseCase(visitorsGateway);
        List<Boolean> isTheCurrentSelectionResults = new ArrayList<>();
        isTheCurrentSelectionUseCase.handle("1", "1").subscribe(isTheCurrentSelectionResults::add);

        // It is
        assertTrue(isTheCurrentSelectionResults.get(0));
    }

    @Test
    public void isTheCurrentSelectionWhenRestaurantIsWithCurrentSession() {
        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();

        // Set selections in gateway
        Selection s1 = new Selection("1", "1");
        Selection s2 = new Selection("1", "2");
        Selection s3 = new Selection("2", "3");
        visitorsGateway.setSelections(Arrays.asList(s1, s2, s3));

        // SUT is the current selection ?
        IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase = new IsTheCurrentSelectionUseCase(visitorsGateway);
        List<Boolean> isTheCurrentSelectionResults = new ArrayList<>();
        isTheCurrentSelectionUseCase.handle("2", "3").subscribe(isTheCurrentSelectionResults::add);
    }
}
