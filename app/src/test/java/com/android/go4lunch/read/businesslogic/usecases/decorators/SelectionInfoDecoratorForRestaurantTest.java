package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.InMemoryHistoricOfSelectionsRepository;
import com.android.go4lunch.InMemoryCurrentSelectionsRepository;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SelectionInfoDecoratorForRestaurantTest {

    private InMemoryCurrentSelectionsRepository selectionQuery;

    private Restaurant restaurant;

    @Before
    public void setUp() {
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        this.selectionQuery = new InMemoryCurrentSelectionsRepository();
        this.restaurant = new Restaurant("Aa", "Ou");
    }

    private void initWithSomeSelections(int selectionsCount) {
        List<Selection> selections = new ArrayList<>();
        for(int i=0; i<selectionsCount; i++) {
            Selection selection = new Selection(this.restaurant, new Workmate("Janie"));
            selections.add(selection);
        }
        this.selectionQuery.setSelections(selections);
    }

    private void assertMatchesSelectionCount(int expectedCount) {
        assert(new SelectionInfoDecoratorForRestaurant(this.selectionQuery)
                .decor(new RestaurantVO(this.restaurant)).getSelectionCountInfo() == expectedCount);
    }

    @Test
    public void shouldReturn1IfRestaurantHas1Selection() {
        this.initWithSomeSelections(1);
        this.assertMatchesSelectionCount(1);
    }

    @Test
    public void shouldReturn2IfRestaurantHas2Selections() {
        this.initWithSomeSelections(2);
        this.assertMatchesSelectionCount(2);
    }

    @Test
    public void shouldReturn3IfRestaurantHas3Selections() {
        this.initWithSomeSelections(3);
        this.assertMatchesSelectionCount(3);
    }

    @Test
    public void shouldReturn0IfRestaurantIsNotTheOneSelected() {
        this.initWithSomeSelections(1);
        Restaurant unselected = new Restaurant("oioi", "nono");
        assert(new SelectionInfoDecoratorForRestaurant(this.selectionQuery)
                .decor(new RestaurantVO(unselected)).getSelectionCountInfo() == 0);
    }

}
