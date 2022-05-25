package com.android.go4lunch;

import com.android.go4lunch.in_memory_gateways.InMemorySessionGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryVisitorGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryWorkmateGateway;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.ui.viewmodels.WorkmateListModel;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.businesslogic.models.WorkmateModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkmateListModelTest {

    InMemorySessionGateway sessionGateway;
    Workmate janieAsTheWorkmateOfTheSession;

    @Before
    public void setUp() {
        this.janieAsTheWorkmateOfTheSession = new Workmate("Janie");
        this.janieAsTheWorkmateOfTheSession.setId("workmateSession");
        this.sessionGateway = new InMemorySessionGateway();
        this.sessionGateway.setSession(janieAsTheWorkmateOfTheSession);
    };

    @Test
    public void listWorkmatesReturnsWorkmatesWhenThereAreSomeAvailableExcludingTheOneOfTheSession() {
        // Prepare Workmate gateway
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        Workmate cyril = new Workmate("Cyril");
        cyril.setId("workmate2");
        workmateGateway.setWorkmates(Arrays.asList(this.janieAsTheWorkmateOfTheSession, cyril));

        // Use Cases
        GetWorkmatesUseCase getWorkmatesUseCase = new GetWorkmatesUseCase(
                workmateGateway,
                this.sessionGateway
        );
        GetWorkmateSelectionUseCase getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(
                new InMemoryVisitorGateway()
        );

        List<WorkmateModel> workmateModelsResults = new ArrayList<>();
        new WorkmateListModel(getWorkmatesUseCase, getWorkmateSelectionUseCase).getWorkmatesWithTheirSelectedRestaurants().subscribe(workmateModelsResults::addAll);
        assert(workmateModelsResults.size() == 1);
    }

    @Test
    public void returnsWorkmatesWithTheirSelections() {
        // Prepare Workmate gateway
        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        Workmate cyril = new Workmate("Cyril");
        cyril.setId("workmate2");
        workmateGateway.setWorkmates(Arrays.asList(this.janieAsTheWorkmateOfTheSession, cyril));

        // Use Cases
        GetWorkmatesUseCase getWorkmatesUseCase = new GetWorkmatesUseCase(
                workmateGateway,
                this.sessionGateway
        );

        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        Selection selection = new Selection("restaurant1", "workmate2");
        selection.setRestaurantName("Chez Lol");
        visitorGateway.setSelections(Arrays.asList(selection));
        GetWorkmateSelectionUseCase getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(visitorGateway);


        WorkmateListModel workmateListModel = new WorkmateListModel(getWorkmatesUseCase, getWorkmateSelectionUseCase);

        List<WorkmateModel> workmateModelsResults = new ArrayList<>();
        workmateListModel.getWorkmatesWithTheirSelectedRestaurants().subscribe(workmateModelsResults::addAll);

        assert(workmateModelsResults.get(0).getSelection().getName().equals("Chez Lol"));
    }
}
