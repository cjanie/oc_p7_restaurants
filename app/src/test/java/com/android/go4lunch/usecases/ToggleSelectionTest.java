package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemorySelectionGateway;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ToggleSelectionTest {

    @Test
    public void toggleSelects() {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();

        ToggleSelection toggleSelection = new ToggleSelection(inMemorySelectionGateway);
        toggleSelection.handle(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));
        Observable<Selection> observableSelection = inMemorySelectionGateway.getSelection();
        assertNotNull(observableSelection);
        List<Selection> results = new ArrayList<>();
        observableSelection.subscribe(results::add);

        assert(results.size() == 1);
    }

    @Test
    public void toggleUnselects() {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        inMemorySelectionGateway.setSelection(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));
        ToggleSelection toggleSelection = new ToggleSelection(inMemorySelectionGateway);
        toggleSelection.handle(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));
        assertNull(inMemorySelectionGateway.getSelection());
    }

}
