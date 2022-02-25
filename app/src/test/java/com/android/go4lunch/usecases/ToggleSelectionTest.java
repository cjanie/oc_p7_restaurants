package com.android.go4lunch.usecases;

import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.gateways_impl.InMemorySessionRepository;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.gateways_impl.InMemoryCurrentSelectionsRepository;
import com.android.go4lunch.gateways_impl.InMemoryHistoricOfSelectionsRepository;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ToggleSelectionTest {

    @Test
    public void toggleShouldAddSelectionIfItDoesNotExist_caseListOfSelectionsIsEmpty() throws NoWorkmateForSessionException {
        // Create a session
        InMemorySessionRepository sessionQuerry = new InMemorySessionRepository();
        sessionQuerry.setWorkmate(new Workmate("Janie"));
        // init selection
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemoryCurrentSelectionsRepository currentSelectionsRepository = new InMemoryCurrentSelectionsRepository();
        // Create restaurant to select
        Restaurant restaurant = new Restaurant("OooResto", "Place A l'Aller-Retour");
        // Create the ToogleSelection
        ToggleSelection toggleSelection = new ToggleSelection(
                currentSelectionsRepository,
                new GetSession(sessionQuerry),
                historicRepository
        );
        // SUT
        toggleSelection.toggle(restaurant);

        Observable<List<Selection>> observableSelections = currentSelectionsRepository.getSelections();
        List<Selection> results = new ArrayList<>();
        observableSelections.subscribe(results::addAll);
        assert(results.size() == 1);
    }

    @Test
    public void toggleShouldRemoveSelectionIfExists_caseSoleElementOfListOfSelections() {
/*
        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate workmate = new Workmate("Janie");
        sessionQuery.setWorkmate(workmate);
        RetrieveSession retrieveSession = new RetrieveSession(sessionQuery);

        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemoryCurrentSelectionsRepository selectionRepository = new InMemoryCurrentSelectionsRepository();

        Restaurant restaurant = new Restaurant("O", "A");
        new ToggleSelection(selectionRepository, retrieveSession, historicRepository).toggle(restaurant);
        assert(selectionRepository.getSelections().size() == 1);

        new ToggleSelection(selectionRepository, retrieveSession, historicRepository).toggle(restaurant);
        assert(selectionRepository.getSelections().isEmpty());

 */
    }

    @Test
    public void toggleShouldAddSelectionIfItDoesNotExistWhenThereAreAlreadySelections() {
/*
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemoryCurrentSelectionsRepository selectionRepository = new InMemoryCurrentSelectionsRepository();
        List<Selection> selections = new ArrayList<>();
        selections.add(new Selection(new Restaurant("O", "A"), new Workmate("J")));
        selectionRepository.setSelections(selections);
        assert(selectionRepository.getSelections().size() == 1);

        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        Restaurant restaurant = new Restaurant("OO", "AA");
        new ToggleSelection(selectionRepository, new RetrieveSession(sessionQuery), historicRepository).toggle(restaurant);
        assert(selectionRepository.getSelections().size() == 2);

 */
    }

    @Test
    public void shouldAddNewSelectionTo2Selections() {
        /*
        // init selection Repo with two selections
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemoryCurrentSelectionsRepository selectionRepository = new InMemoryCurrentSelectionsRepository();
        List<Selection> selections = new ArrayList<>();
        Restaurant restaurant0 = new Restaurant("O", "A");
        Selection selection1 = new Selection(restaurant0, new Workmate("J"));
        selections.add(selection1);
        Selection selection2 = new Selection(restaurant0, new Workmate("JJ"));
        selections.add(selection2);
        selectionRepository.setSelections(selections);
        assert(selectionRepository.getSelections().size() == 2);

        // init session
        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        RetrieveSession retrieveSession = new RetrieveSession(sessionQuery);

        // Create Restaurant for selection
        Restaurant restaurant = new Restaurant("OO", "AA");

        // Select restaurant
        new ToggleSelection(selectionRepository, retrieveSession, historicRepository).toggle(restaurant);

        // Check that selection has been added in repo
        assert(selectionRepository.getSelections().size() == 3);

         */
    }

    @Test
    public void toggleShouldRemoveExistingSelectionAmongOthers() {
/*
        // init selection Repo with one selection
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemoryCurrentSelectionsRepository selectionRepository = new InMemoryCurrentSelectionsRepository();
        List<Selection> selections = new ArrayList<>();
        Restaurant restaurant0 = new Restaurant("O", "A");
        Selection selection1 = new Selection(restaurant0, new Workmate("J"));
        selections.add(selection1);
        Selection selection2 = new Selection(restaurant0, new Workmate("JJ"));
        selections.add(selection2);
        selectionRepository.setSelections(selections);
        assert(selectionRepository.getSelections().size() == 2);

        // init session
        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        RetrieveSession retrieveSession = new RetrieveSession(sessionQuery);

        // Create Restaurant for selection
        Restaurant restaurant = new Restaurant("OO", "AA");

        // Select restaurant
        new ToggleSelection(selectionRepository, retrieveSession, historicRepository).toggle(restaurant);

        // Check that selection has been added in repo
        assert(selectionRepository.getSelections().size() == 3);

        new ToggleSelection(selectionRepository, retrieveSession, historicRepository).toggle(restaurant);
        assert(selectionRepository.getSelections().size() == 2);

 */
    }

    @Test
    public void newSelectionShouldRemovePreviousSelectionOfTheSameWorkmate() {
        /*
        // init selection Repo with one selection
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemoryCurrentSelectionsRepository selectionRepository = new InMemoryCurrentSelectionsRepository();
        assert(selectionRepository.getSelections().size() == 0);

        // init session
        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        RetrieveSession retrieveSession = new RetrieveSession(sessionQuery);

        // Create Restaurant for selection
        Restaurant restaurant = new Restaurant("OO", "AA");

        // Select restaurant
        new ToggleSelection(selectionRepository, retrieveSession, historicRepository).toggle(restaurant);

        // Check that selection has been added in repo
        assert(selectionRepository.getSelections().size() == 1);
        Restaurant restaurant2 = new Restaurant("OIE", "YO");
        new ToggleSelection(selectionRepository, retrieveSession, historicRepository).toggle(restaurant2);
        assert(selectionRepository.getSelections().size() == 1);

         */
    }

/*
    @Test
    public void onAddSelectionShouldUpdateHistoricWhenHistoricIsNotEmpty() {

        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemoryCurrentSelectionsRepository selectionRepository = new InMemoryCurrentSelectionsRepository();
        List<Selection> selections = new ArrayList<>();
        selections.add(new Selection(new Restaurant("O", "A"), new Workmate("J")));
        selectionRepository.setSelections(selections);
        assert(selectionRepository.getSelections().size() == 1);

        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        Restaurant restaurant = new Restaurant("OO", "AA");
        new ToggleSelection(selectionRepository, new RetrieveSession(sessionQuery), historicRepository).toggle(restaurant);
        assert(historicRepository.getCount(restaurant) == 1);
    }

    @Test
    public void onAddSelectionShouldFillEmptyHistoric() {
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemoryCurrentSelectionsRepository selectionRepository = new InMemoryCurrentSelectionsRepository();
        assert(selectionRepository.getSelections().size() == 0);

        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        Workmate me = new Workmate("JJ");
        sessionQuery.setWorkmate(me);
        Restaurant restaurant = new Restaurant("OO", "AA");
        int countInit = historicRepository.getCount(restaurant);
        new ToggleSelection(selectionRepository, new RetrieveSession(sessionQuery), historicRepository).toggle(restaurant);
        assert(selectionRepository.getSelections().size() == 1);
        assert(historicRepository.getCount(restaurant) == countInit + 1);
    }

*/

}
