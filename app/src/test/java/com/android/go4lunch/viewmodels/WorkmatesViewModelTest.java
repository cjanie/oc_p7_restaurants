package com.android.go4lunch.viewmodels;

import com.android.go4lunch.gateways_impl.InMemoryVisitorGateway;
import com.android.go4lunch.gateways_impl.Mock;
import com.android.go4lunch.in_memory_gateways.InMemoryRestaurantGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryWorkmateGateway;
import com.android.go4lunch.ui.viewmodels.WorkmatesViewModel;
import com.android.go4lunch.usecases.GetRestaurantByIdUseCase;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;

import org.junit.Test;

public class WorkmatesViewModelTest {

    @Test
    public void listsWorkmates() {
        Mock mock = new Mock();

        InMemoryWorkmateGateway workmateGateway = new InMemoryWorkmateGateway();
        workmateGateway.setWorkmates(mock.workmates());
        GetWorkmatesUseCase getWorkmatesUseCase = new GetWorkmatesUseCase(workmateGateway);

        InMemoryVisitorGateway visitorsGateway = new InMemoryVisitorGateway();
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
