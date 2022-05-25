package com.android.go4lunch.usecases;

import com.android.go4lunch.in_memory_gateways.InMemoryVisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class GetRestaurantVisitorsUseCaseTest {

    @Test
    public void returnsVisitorsWhenThereAreSome () {
        InMemoryVisitorGateway restaurantVisitorGateway = new InMemoryVisitorGateway();
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
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(visitorGateway);
        List<String> results = new ArrayList<>();
        getRestaurantVisitorsUseCase.handle("0").subscribe(results::addAll);
        assert(results.isEmpty());
    }

    @Test
    public void returnsVisitorsOfARestaurantAmongOthers() {
        InMemoryVisitorGateway visitorsGateway = new InMemoryVisitorGateway();
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
