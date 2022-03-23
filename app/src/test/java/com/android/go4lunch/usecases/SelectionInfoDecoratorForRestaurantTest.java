package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemorySelectionGateway;
import com.android.go4lunch.models.Restaurant;

import org.junit.Before;

public class SelectionInfoDecoratorForRestaurantTest {

    private InMemorySelectionGateway selectionGateway;

    private Restaurant restaurant;

    @Before
    public void setUp() {
        this.selectionGateway = new InMemorySelectionGateway();
        this.restaurant = new Restaurant("Aa", "Ou");
    }

}
