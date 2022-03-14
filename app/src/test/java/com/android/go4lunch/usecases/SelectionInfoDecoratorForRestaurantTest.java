package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways_impl.InMemorySelectionGateway;
import com.android.go4lunch.usecases.decorators.SelectionInfoDecoratorForRestaurant;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class SelectionInfoDecoratorForRestaurantTest {

    private InMemorySelectionGateway selectionGateway;

    private Restaurant restaurant;

    @Before
    public void setUp() {
        this.selectionGateway = new InMemorySelectionGateway();
        this.restaurant = new Restaurant("Aa", "Ou");
    }

}
