package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class GetRestaurantVisitorsUseCaseTest {

    @Test
    public void returnsVisitorsWhenThereAreSome () {
        InMemoryVisitorsGateway restaurantVisitorGateway = new InMemoryVisitorsGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("1", "Resto", "1", "Janie");
        selections.add(selection);
        restaurantVisitorGateway.setSelections(selections);
        GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(restaurantVisitorGateway);
        assertFalse(getRestaurantVisitorsUseCase.handle("1").isEmpty());
    }

    @Test
    public void doesNotReturnAnyVisitorWhenThereIsNone() {
        InMemoryVisitorsGateway visitorGateway = new InMemoryVisitorsGateway();
        GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(visitorGateway);
        List<Workmate> visitors = getRestaurantVisitorsUseCase.handle("0");
        assert(visitors.isEmpty());
    }

    @Test
    public void returnsVisitorsOfARestaurantAmongOthers() {
        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();
        List<Selection> selected = new ArrayList<>();
        Selection selection1 = new Selection(
                "1", "Resto",
                "1", "Janie");
        selected.add(selection1);
        Selection selection2 = new Selection(
                "2", "Miam",
                "2", "Cyril");
        selected.add(selection2);
        visitorsGateway.setSelections(selected);
        Selection selection3 = new Selection(
                "1", "Resto",
                "2", "Cyril");
        selected.add(selection3);
        GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(visitorsGateway);
        List<Workmate> visitors = getRestaurantVisitorsUseCase.handle("1");
        assert(visitors.size() == 2);
    }

}
