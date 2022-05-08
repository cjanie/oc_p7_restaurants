package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemoryVisitorGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class GetWorkmateSelectionUseCaseTest {

    @Test
    public void retrievesWorkmateSelectionWhenThereIsOne() throws NotFoundException {
        InMemoryVisitorGateway visitorsGateway = new InMemoryVisitorGateway();
        Selection selection1 = new Selection(
                "1",
                "1"
                );
        Selection selection2 = new Selection(
                "2",
                "2"
        );
        visitorsGateway.setSelections(Arrays.asList(selection1, selection2));

        GetWorkmateSelectionUseCase getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(visitorsGateway);
        List<String> selectionResults = new ArrayList<>();
        getWorkmateSelectionUseCase.handle("2").subscribe(selectionResults::add);
        assert(selectionResults.get(0).equals("2"));
    }

    @Test
    public void nothingWhenWormateHasNoSelection() {
        InMemoryVisitorGateway visitorsGateway = new InMemoryVisitorGateway();
        GetWorkmateSelectionUseCase getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(visitorsGateway);
        assertThrows(NotFoundException.class, () -> getWorkmateSelectionUseCase.handle("1"));
    }
}
