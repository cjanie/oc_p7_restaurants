package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemorySelectionGateway;
import com.android.go4lunch.in_memory_repositories.InMemoryVisitorsGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LikeForLunchUseCaseTest {



    private List<Selection> commandSelect(String restaurantId, String restaurantName) {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();

        LikeForLunchUseCase likeForLunchUseCase = new LikeForLunchUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway
        );
        likeForLunchUseCase.handle(
                restaurantId,
                restaurantName,
                "1",
                "Janie"
        );
        Observable<Selection> observableSelection = inMemorySelectionGateway.getSelection();
        assertNotNull(observableSelection);
        List<Selection> results = new ArrayList<>();
        observableSelection.subscribe(results::add);
        return results;
    }

    private Observable<Selection> commandUnselect(String restaurantId, String restaurantName) {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        inMemorySelectionGateway.setSelection(new Selection(
                restaurantId,
                restaurantName,
                "1",
                "Janie"
        ));
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();

        LikeForLunchUseCase likeForLunchUseCase = new LikeForLunchUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway
        );
        likeForLunchUseCase.handle(restaurantId, restaurantName,"1", "Janie");

        return inMemorySelectionGateway.getSelection();
    }

    @Test
    public void toggleSelects() {
        assert(this.commandSelect("1", "Chez Jojo").size() == 1);
    }

    @Test
    public void toggleUnselects() {
        assertNull(this.commandUnselect("1", "Chez Jojo"));
    }

    @Test
    public void likeForLunchIncrementsVisitors() {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("2", "Resto", "2", "Cyril");
        selections.add(selection);
        inMemoryVisitorsGateway.setSelections(selections);

        LikeForLunchUseCase likeForLunchUseCase = new LikeForLunchUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway
        );
        likeForLunchUseCase.handle("1", "Chez Jojo", "1", "Janie");

        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);

        assert(savedSelections.size() == 2);
    }

    @Test
    public void cancelSelectionDecrementsVisitors() {
        InMemorySelectionGateway inMemorySelectionGateway = new InMemorySelectionGateway();
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();

        LikeForLunchUseCase likeForLunchUseCase = new LikeForLunchUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway
        );
        likeForLunchUseCase.handle("1", "Chez Jojo", "1", "Janie");

        likeForLunchUseCase.handle(
                "1", "Chez Jojo", "1", "Janie");

        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);
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

        LikeForLunchUseCase likeForLunchUseCase = new LikeForLunchUseCase(
                inMemorySelectionGateway,
                inMemoryVisitorsGateway
        );
                likeForLunchUseCase.handle("2","Chez Jojo", "2", "Janie");
        likeForLunchUseCase.handle("2", "Chez Jojo", "2", "janie");
        List<Selection> savedSelections = new ArrayList<>();
        inMemoryVisitorsGateway.getSelections().subscribe(savedSelections::addAll);
        assert(savedSelections.size() == 1);
        assert(savedSelections.get(0).getWorkmateName().equals("Cyril"));
    }

}
