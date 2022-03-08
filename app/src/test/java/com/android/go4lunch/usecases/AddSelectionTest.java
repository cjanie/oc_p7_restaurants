package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemorySelectionGateway;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class AddSelectionTest {

    @Test
    public void addSelectionOnceShouldIncrementWithOneSelection() {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();

        AddSelection addSelection = new AddSelection(inMemorySelectionGateway);
        addSelection.add(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));
        Observable<List<Selection>> observableSelections = inMemorySelectionGateway.getSelections();
        List<Selection> results = new ArrayList<>();
        observableSelections.subscribe(results::addAll);

        assert(!results.isEmpty());
        assert(results.size() == 1);
    }


    @Test
    public void addSelectionTwiceShouldIncrementWith2Selections() {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();

        AddSelection addSelection = new AddSelection(inMemorySelectionGateway);
        addSelection.add(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));
        addSelection.add(new Selection(
                new Restaurant("Chez Lou", "Avenue des Loulous"),
                new Workmate("Jojo")
        ));
        Observable<List<Selection>> observableSelections = inMemorySelectionGateway.getSelections();
        List<Selection> results = new ArrayList<>();
        observableSelections.subscribe(results::addAll);

        assert(results.size() == 2);
    }
}
