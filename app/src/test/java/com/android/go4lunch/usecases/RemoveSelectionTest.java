package com.android.go4lunch.usecases;

import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.repositories.InMemoryCurrentSelectionsRepository;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class RemoveSelectionTest {


    @Test
    public void addSelectionOnceShouldDecrementWithOneSelection() {
        InMemoryCurrentSelectionsRepository inMemoryCurrentSelectionsRepository = new InMemoryCurrentSelectionsRepository();
        inMemoryCurrentSelectionsRepository.setSelections(Arrays.asList(
                new Selection(
                        new Restaurant("Chez Jojo", "Avenue des Loulous"),
                        new Workmate("Janie")
                ),
                new Selection(
                        new Restaurant("Chez Jojo", "Avenue des Loulous"),
                        new Workmate("Cyril")
                )
        ));

        RemoveSelection removeSelection = new RemoveSelection(inMemoryCurrentSelectionsRepository);
        removeSelection.remove(new Selection(
                new Restaurant("Chez Jojo", "Avenue des Loulous"),
                new Workmate("Janie")
        ));

        Observable<List<Selection>> observableSelections = inMemoryCurrentSelectionsRepository.getSelections();
        List<Selection> results = new ArrayList<>();
        observableSelections.subscribe(results::addAll);

        assert(!results.isEmpty());
        assert(results.size() == 1);
    }

}
