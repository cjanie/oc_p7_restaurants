package com.android.go4lunch.usecases;

import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.gateways_impl.InMemorySelectionGateway;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class RemoveSelectionTest {


    @Test
    public void addSelectionOnceShouldDecrementWithOneSelection() {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        inMemorySelectionGateway.setSelections(Arrays.asList(
                new Selection(
                        new Restaurant("Chez Jojo", "Avenue des Loulous"),
                        new Workmate("Janie")
                ),
                new Selection(
                        new Restaurant("Chez Jojo", "Avenue des Loulous"),
                        new Workmate("Cyril")
                )
        ));

        RemoveSelection removeSelection = new RemoveSelection(inMemorySelectionGateway);
        removeSelection.remove(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));

        Observable<List<Selection>> observableSelections = inMemorySelectionGateway.getSelections();
        List<Selection> results = new ArrayList<>();
        observableSelections.subscribe(results::addAll);

        assert(!results.isEmpty());
        assert(results.size() == 1);
    }

}
