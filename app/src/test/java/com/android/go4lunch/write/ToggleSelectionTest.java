package com.android.go4lunch.write;

import com.android.go4lunch.InMemorySelectionRepository;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;
import com.android.go4lunch.write.businesslogic.usecases.ToggleSelection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToggleSelectionTest {

    @Test
    public void toggleShouldAddSelectionIfItDoesNotExist() {
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository();
        Selection selection = new Selection(new Restaurant("O", "A"), new Workmate("J"));
        new ToggleSelection(selectionRepository, selection).toggle();
        assert(selectionRepository.findAll().size() == 1);
    }

    @Test
    public void toggleShouldRemoveSelectionIfExists() {
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository();
        Selection selection = new Selection(new Restaurant("O", "A"), new Workmate("J"));
        new ToggleSelection(selectionRepository, selection).toggle();
        assert(selectionRepository.findAll().contains(selection));
        new ToggleSelection(selectionRepository, selection).toggle();
        assert(selectionRepository.findAll().isEmpty());
    }

    @Test
    public void toggleShouldAddSelectionIfItDoesNotExistWhenThereAreAlreadySelections() {
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository();
        List<Selection> selections = new ArrayList<>();
        selections.add(new Selection(new Restaurant("O", "A"), new Workmate("J")));
        selectionRepository.setSelections(selections);
        assert(selectionRepository.findAll().size() == 1);

        Selection selection = new Selection(new Restaurant("OO", "AA"), new Workmate("JJ"));
        new ToggleSelection(selectionRepository, selection).toggle();
        assert(selectionRepository.findAll().size() == 2);
    }

    @Test
    public void toggleShouldRemoveExistingSelectionAmongOthers() {
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository();
        List<Selection> selections = new ArrayList<>();
        Selection selection0 = new Selection(new Restaurant("O", "A"), new Workmate("J"));
        selections.add(selection0);
        selectionRepository.setSelections(selections);
        assert(selectionRepository.findAll().size() == 1);

        Selection selection = new Selection(new Restaurant("OO", "AA"), new Workmate("JJ"));
        new ToggleSelection(selectionRepository, selection).toggle();
        assert(selectionRepository.findAll().size() == 2);

        new ToggleSelection(selectionRepository, selection0).toggle();
        assert(selectionRepository.findAll().size() == 1);
    }
}
