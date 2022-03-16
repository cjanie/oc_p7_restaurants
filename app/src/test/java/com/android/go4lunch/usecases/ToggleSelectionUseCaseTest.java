package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.gateways_impl.InMemorySelectionGateway;
import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ToggleSelectionUseCaseTest {

    private List<Selection> commandSelect(Selection selection) {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(inMemorySelectionGateway, inMemoryVisitorsGateway);
        toggleSelectionUseCase.handle(selection);
        Observable<Selection> observableSelection = inMemorySelectionGateway.getSelection();
        assertNotNull(observableSelection);
        List<Selection> results = new ArrayList<>();
        observableSelection.subscribe(results::add);
        return results;
    }

    private Observable<Selection> commandUnselect(Selection selection) {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        inMemorySelectionGateway.setSelection(selection);

        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();

        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(inMemorySelectionGateway, inMemoryVisitorsGateway);
        toggleSelectionUseCase.handle(selection);

        return inMemorySelectionGateway.getSelection();
    }



    @Test
    public void toggleSelects() {
        Selection selection = new Selection(
                "1", "Chez Jojo",
                "1", "janie");
        assert(this.commandSelect(selection).size() == 1);
    }

    @Test
    public void toggleUnselects() {
        Selection selection = new Selection(
                "1", "Chez Jojo",
                "1", "janie");
        assertNull(this.commandUnselect(selection));
    }

    @Test
    public void likeForLunchIncrementsVisitors() {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("2", "Resto", "2", "Cyril");
        selections.add(selection);
        inMemoryVisitorsGateway.setSelections(selections);
        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway
        );

        toggleSelectionUseCase.handle(new Selection(
                "1", "Chez Jojo",
                "1", "janie"));

        List<Selection> savedSelections = inMemoryVisitorsGateway.getSelections();

        assert(savedSelections.size() == 2);
    }

    @Test
    public void cancelSelectionDecrementsVisitors() {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(inMemorySelectionGateway, inMemoryVisitorsGateway);


        toggleSelectionUseCase.handle(new Selection(
                "1", "Chez Jojo",
                "1", "janie"));

        toggleSelectionUseCase.handle(new Selection(
                "1", "Chez Jojo",
                "1", "janie"));

        List<Selection> savedSelections = inMemoryVisitorsGateway.getSelections();
        assert(savedSelections.size() == 0);
    }

    @Test
    public void cancelSelectionDeletesTheSelectionOfTheUser() {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();

        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        List<Selection> selections = new ArrayList<>();
        Workmate cyril = new Workmate("Cyril");
        cyril.setId("1");
        Selection selection = new Selection(
                "1",
                "Resto",
                "1",
                "Cyril");
        selections.add(selection);
        inMemoryVisitorsGateway.setSelections(selections);

        Workmate janie = new Workmate("Janie");
        janie.setId("2");
        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(inMemorySelectionGateway, inMemoryVisitorsGateway);
        toggleSelectionUseCase.handle(new Selection(

                        "2","Chez Jojo",
                        "2", "Janie"));
        toggleSelectionUseCase.handle(new Selection(
                "2", "Chez Jojo",
                "2", "Janie"));
        List<Selection> savedSelections = inMemoryVisitorsGateway.getSelections();
        assert(savedSelections.size() == 1);
        assert(savedSelections.get(0).getWorkmateName().equals("Cyril"));
    }

}
