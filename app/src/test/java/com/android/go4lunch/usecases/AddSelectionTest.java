package com.android.go4lunch.usecases;

import com.android.go4lunch.repositories.InMemoryCurrentSelectionsRepository;
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
        InMemoryCurrentSelectionsRepository inMemoryCurrentSelectionsRepository = new InMemoryCurrentSelectionsRepository();

        AddSelection addSelection = new AddSelection(inMemoryCurrentSelectionsRepository);
        addSelection.add(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));
        Observable<List<Selection>> observableSelections = inMemoryCurrentSelectionsRepository.getSelections();
        List<Selection> results = new ArrayList<>();
        observableSelections.subscribe(results::addAll);

        assert(!results.isEmpty());
        assert(results.size() == 1);
    }


    @Test
    public void addSelectionTwiceShouldIncrementWith2Selections() {
        InMemoryCurrentSelectionsRepository inMemoryCurrentSelectionsRepository = new InMemoryCurrentSelectionsRepository();

        AddSelection addSelection = new AddSelection(inMemoryCurrentSelectionsRepository);
        addSelection.add(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));
        addSelection.add(new Selection(
                new Restaurant("Chez Lou", "Avenue des Loulous"),
                new Workmate("Jojo")
        ));
        Observable<List<Selection>> observableSelections = inMemoryCurrentSelectionsRepository.getSelections();
        List<Selection> results = new ArrayList<>();
        observableSelections.subscribe(results::addAll);

        assert(results.size() == 2);
    }
}
