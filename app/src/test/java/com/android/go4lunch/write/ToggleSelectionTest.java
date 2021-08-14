package com.android.go4lunch.write;

import com.android.go4lunch.InMemoryHistoricOfSelectionsRepository;
import com.android.go4lunch.InMemorySelectionRepository;
import com.android.go4lunch.read.adapter.InMemorySessionQuery;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveSession;
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
    public void toggleShouldAddSelectionIfItDoesNotExist_caseListOfSelectionsIsEmpty() {

        // Create a session
        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate workmate = new Workmate("J");
        sessionQuery.setWorkmate(workmate);

        // init selection
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository(historicRepository);
        Restaurant restaurant = new Restaurant("O", "A");

        new ToggleSelection(selectionRepository, new RetrieveSession(sessionQuery), restaurant).toggle();
        assert(selectionRepository.findAll().size() == 1);


    }

    @Test
    public void toggleShouldRemoveSelectionIfExists_caseSoleElementOfListOfSelections() {

        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate workmate = new Workmate("Janie");
        sessionQuery.setWorkmate(workmate);
        RetrieveSession retrieveSession = new RetrieveSession(sessionQuery);

        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository(historicRepository);

        Restaurant restaurant = new Restaurant("O", "A");
        new ToggleSelection(selectionRepository, retrieveSession, restaurant).toggle();
        assert(selectionRepository.findAll().size() == 1);

        new ToggleSelection(selectionRepository, retrieveSession, restaurant).toggle();
        assert(selectionRepository.findAll().isEmpty());
    }

    @Test
    public void toggleShouldAddSelectionIfItDoesNotExistWhenThereAreAlreadySelections() {

        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository(historicRepository);
        List<Selection> selections = new ArrayList<>();
        selections.add(new Selection(new Restaurant("O", "A"), new Workmate("J")));
        selectionRepository.setSelections(selections);
        assert(selectionRepository.findAll().size() == 1);

        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        Restaurant restaurant = new Restaurant("OO", "AA");
        new ToggleSelection(selectionRepository, new RetrieveSession(sessionQuery), restaurant).toggle();
        assert(selectionRepository.findAll().size() == 2);
    }

    @Test
    public void shouldAddNewSelectionTo2Selections() {
        // init selection Repo with two selections
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository(historicRepository);
        List<Selection> selections = new ArrayList<>();
        Restaurant restaurant0 = new Restaurant("O", "A");
        Selection selection1 = new Selection(restaurant0, new Workmate("J"));
        selections.add(selection1);
        Selection selection2 = new Selection(restaurant0, new Workmate("JJ"));
        selections.add(selection2);
        selectionRepository.setSelections(selections);
        assert(selectionRepository.findAll().size() == 2);

        // init session
        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        RetrieveSession retrieveSession = new RetrieveSession(sessionQuery);

        // Create Restaurant for selection
        Restaurant restaurant = new Restaurant("OO", "AA");

        // Select restaurant
        new ToggleSelection(selectionRepository, retrieveSession, restaurant).toggle();

        // Check that selection has been added in repo
        assert(selectionRepository.findAll().size() == 3);
    }

    @Test
    public void toggleShouldRemoveExistingSelectionAmongOthers() {

        // init selection Repo with one selection
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository(historicRepository);
        List<Selection> selections = new ArrayList<>();
        Restaurant restaurant0 = new Restaurant("O", "A");
        Selection selection1 = new Selection(restaurant0, new Workmate("J"));
        selections.add(selection1);
        Selection selection2 = new Selection(restaurant0, new Workmate("JJ"));
        selections.add(selection2);
        selectionRepository.setSelections(selections);
        assert(selectionRepository.findAll().size() == 2);

        // init session
        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        RetrieveSession retrieveSession = new RetrieveSession(sessionQuery);

        // Create Restaurant for selection
        Restaurant restaurant = new Restaurant("OO", "AA");

        // Select restaurant
        new ToggleSelection(selectionRepository, retrieveSession, restaurant).toggle();

        // Check that selection has been added in repo
        assert(selectionRepository.findAll().size() == 3);

        new ToggleSelection(selectionRepository, retrieveSession, restaurant).toggle();
        assert(selectionRepository.findAll().size() == 2);
    }

    @Test
    public void newSelectionShouldRemovePreviousSelectionOfTheSameWorkmate() {
        // init selection Repo with one selection
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository(historicRepository);
        assert(selectionRepository.findAll().size() == 0);

        // init session
        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        RetrieveSession retrieveSession = new RetrieveSession(sessionQuery);

        // Create Restaurant for selection
        Restaurant restaurant = new Restaurant("OO", "AA");

        // Select restaurant
        new ToggleSelection(selectionRepository, retrieveSession, restaurant).toggle();

        // Check that selection has been added in repo
        assert(selectionRepository.findAll().size() == 1);
        Restaurant restaurant2 = new Restaurant("OIE", "YO");
        new ToggleSelection(selectionRepository, retrieveSession, restaurant2);
        assert(selectionRepository.findAll().size() == 1);
    }


    @Test
    public void onAddSelectionShouldUpdateHistoricWhenHistoricIsNotEmpty() {

        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository(historicRepository);
        List<Selection> selections = new ArrayList<>();
        selections.add(new Selection(new Restaurant("O", "A"), new Workmate("J")));
        selectionRepository.setSelections(selections);
        assert(selectionRepository.findAll().size() == 1);

        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        Restaurant restaurant = new Restaurant("OO", "AA");
        new ToggleSelection(selectionRepository, new RetrieveSession(sessionQuery), restaurant).toggle();
        assert(historicRepository.getCount(restaurant) == 1);
    }

    @Test
    public void onAddSelectionShouldUpdateHistoricWhenHistoricIsEmpty() {
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemorySelectionRepository selectionRepository = new InMemorySelectionRepository(historicRepository);
        assert(selectionRepository.findAll().size() == 0);

        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        Restaurant restaurant = new Restaurant("OO", "AA");
        int countInit = historicRepository.getCount(restaurant);
        new ToggleSelection(selectionRepository, new RetrieveSession(sessionQuery), restaurant).toggle();
        assert(selectionRepository.findAll().size() == 1);
        assert(historicRepository.getCount(restaurant) == countInit + 1);
    }



}
