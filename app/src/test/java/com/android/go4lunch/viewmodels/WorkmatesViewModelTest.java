package com.android.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;

import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.gateways_impl.Mock;
import com.android.go4lunch.in_memory_repositories.InMemoryRestaurantGateway;
import com.android.go4lunch.in_memory_repositories.InMemoryWorkmateGateway;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.ui.viewmodels.WorkmatesViewModel;
import com.android.go4lunch.usecases.GetRestaurantByIdUseCase;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.usecases.models.WorkmateModel;

import org.junit.Test;

import java.util.List;

public class WorkmatesViewModelTest {

    @Test
    public void listsWorkmates() {
        Mock mock = new Mock();

        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        workmateGateway.setWorkmates(mock.workmates());
        GetWorkmatesUseCase getWorkmatesUseCase = new GetWorkmatesUseCase(workmateGateway);

        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();
        visitorsGateway.setSelections(mock.selections());
        GetWorkmateSelectionUseCase getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(visitorsGateway);

        InMemoryRestaurantGateway restaurantGateway = new InMemoryRestaurantGateway();
        restaurantGateway.setRestaurants(mock.restaurants());
        GetRestaurantByIdUseCase getRestaurantByIdUseCase = new GetRestaurantByIdUseCase(restaurantGateway);

        WorkmatesViewModel workmatesViewModel = new WorkmatesViewModel(
                getWorkmatesUseCase, getWorkmateSelectionUseCase, getRestaurantByIdUseCase
        );

        //assert(workmatesViewModel.getWorkmates().size() == mock.workmates().size());
    }

}
