package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.models.Selection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class GetRestaurantVisitorsUseCaseTest {

    @Test
    public void returnsVisitorsWhenThereAreSome () {
        InMemoryVisitorsGateway restaurantVisitorGateway = new InMemoryVisitorsGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection = new Selection("1", "1");
        selections.add(selection);
        restaurantVisitorGateway.setSelections(selections);
        GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(restaurantVisitorGateway);
        List<String> results = new ArrayList<>();
        getRestaurantVisitorsUseCase.handle("1").subscribe(results::addAll);
        assertFalse(results.isEmpty());
    }

    @Test
    public void doesNotReturnAnyVisitorWhenThereIsNone() {
        InMemoryVisitorsGateway visitorGateway = new InMemoryVisitorsGateway();
        GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(visitorGateway);
        List<String> results = new ArrayList<>();
        getRestaurantVisitorsUseCase.handle("0").subscribe(results::addAll);
        assert(results.isEmpty());
    }

    @Test
    public void returnsVisitorsOfARestaurantAmongOthers() {
        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();
        List<Selection> selected = new ArrayList<>();
        Selection selection1 = new Selection("1", "1");
        selected.add(selection1);
        Selection selection2 = new Selection("2", "2");
        selected.add(selection2);
        visitorsGateway.setSelections(selected);
        Selection selection3 = new Selection("1", "2");
        selected.add(selection3);
        GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(visitorsGateway);
        List<String> results = new ArrayList<>();
        getRestaurantVisitorsUseCase.handle("1").subscribe(results::addAll);
        assert(results.size() == 2);
    }

}
