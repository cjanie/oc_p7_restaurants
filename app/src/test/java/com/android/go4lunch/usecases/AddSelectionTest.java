package com.android.go4lunch.usecases;

import com.android.go4lunch.InMemoryCurrentSelectionsRepository;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;

public class AddSelectionTest {

    @Test
    public void addSelectionShouldIncrementSet() {
        InMemoryCurrentSelectionsRepository inMemoryCurrentSelectionsRepository = new InMemoryCurrentSelectionsRepository();

        AddSelection addSelection = new AddSelection(inMemoryCurrentSelectionsRepository);
        addSelection.add(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));
        Observable<Set<Selection>> observableSelections = inMemoryCurrentSelectionsRepository.getSelections();
        List<Selection> results = new ArrayList<>();
        observableSelections.subscribe(results::addAll);

        assert(!results.isEmpty());
    }


    @Test
    public void addSelectionShouldIncrementTwiceWithASecondSameSelection() {
        InMemoryCurrentSelectionsRepository inMemoryCurrentSelectionsRepository = new InMemoryCurrentSelectionsRepository();

        AddSelection addSelection = new AddSelection(inMemoryCurrentSelectionsRepository);
        addSelection.add(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));
        addSelection.add(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));
        Observable<Set<Selection>> observableSelections = inMemoryCurrentSelectionsRepository.getSelections();
        List<Selection> results = new ArrayList<>();
        observableSelections.subscribe(results::addAll);

        assert(results.size() == 1);
    }

    @Test
    public void addSelectionShouldIncrementTwiceWhenASecondNotSameSelection() {
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
        Observable<Set<Selection>> observableSelections = inMemoryCurrentSelectionsRepository.getSelections();
        List<Selection> results = new ArrayList<>();
        observableSelections.subscribe(results::addAll);

        assert(results.size() == 2);
    }
}
