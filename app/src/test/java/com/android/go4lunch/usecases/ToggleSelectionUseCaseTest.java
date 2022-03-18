package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.gateways_impl.InMemorySelectionGateway;
import com.android.go4lunch.gateways_impl.InMemorySessionGateway;
import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ToggleSelectionUseCaseTest {



    private List<Selection> commandSelect(String restaurantId, String restaurantName) throws NoWorkmateForSessionException {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("1");
        inMemorySessionGateway.setWorkmate(session);
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(inMemorySessionGateway);

        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway,
                getSessionUseCase
        );
        toggleSelectionUseCase.handle(
                restaurantId,
                restaurantName
        );
        Observable<Selection> observableSelection = inMemorySelectionGateway.getSelection();
        assertNotNull(observableSelection);
        List<Selection> results = new ArrayList<>();
        observableSelection.subscribe(results::add);
        return results;
    }

    private Observable<Selection> commandUnselect(String restaurantId, String restaurantName) throws NoWorkmateForSessionException {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        inMemorySelectionGateway.setSelection(new Selection(
                restaurantId,
                restaurantName,
                "1",
                "Janie"
        ));
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        Workmate session = new Workmate("Janie");
        session.setId("1");

        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        inMemorySessionGateway.setWorkmate(session);
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(inMemorySessionGateway);
        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway,
                getSessionUseCase
        );
        toggleSelectionUseCase.handle(restaurantId, restaurantName);

        return inMemorySelectionGateway.getSelection();
    }



    @Test
    public void toggleSelects() throws NoWorkmateForSessionException {
        assert(this.commandSelect("1", "Chez Jojo").size() == 1);
    }

    @Test
    public void toggleUnselects() throws NoWorkmateForSessionException {
        assertNull(this.commandUnselect("1", "Chez Jojo"));
    }

    @Test
    public void likeForLunchIncrementsVisitors() throws NoWorkmateForSessionException {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("2", "Resto", "2", "Cyril");
        selections.add(selection);
        inMemoryVisitorsGateway.setSelections(selections);
        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("1");
        inMemorySessionGateway.setWorkmate(session);
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(inMemorySessionGateway);
        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway,
                getSessionUseCase
        );
        toggleSelectionUseCase.handle("1", "Chez Jojo");

        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);

        assert(savedSelections.size() == 2);
    }

    @Test
    public void cancelSelectionDecrementsVisitors() throws NoWorkmateForSessionException {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("1");
        inMemorySessionGateway.setWorkmate(session);
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(inMemorySessionGateway);
        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway,
                getSessionUseCase
        );
        toggleSelectionUseCase.handle("1", "Chez Jojo");

        toggleSelectionUseCase.handle(
                "1", "Chez Jojo");

        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 0);
    }

    @Test
    public void cancelSelectionDeletesTheSelectionOfTheUser() throws NoWorkmateForSessionException {
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
        InMemorySessionGateway inMemorySessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Janie");
        session.setId("2");
        inMemorySessionGateway.setWorkmate(session);
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(inMemorySessionGateway);
        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway,
                getSessionUseCase
        );
                toggleSelectionUseCase.handle("2","Chez Jojo");
        toggleSelectionUseCase.handle("2", "Chez Jojo");
        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 1);
        assert(savedSelections.get(0).getWorkmateName().equals("Cyril"));
    }

}
