package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.adapter.InMemorySelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.WorkmateVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SelectionInfoDecoratorForWorkmateTest {

    @Test
    public void janieHasSelectedRestaurant1() {
        InMemorySelectionQuery selectionQuery = new InMemorySelectionQuery();
        Restaurant restaurant = new Restaurant("r1", "loc");
        Workmate workmate = new Workmate("Janie");
        Selection selection = new Selection(restaurant, workmate);
        selectionQuery.setSelections(Arrays.asList(new Selection[]{selection}));

        WorkmateVO workmateVO = new SelectionInfoDecoratorForWorkMate(selectionQuery, new WorkmateVO(workmate))
                .decor();

        assert(workmateVO.getSelection().getName().equals(restaurant.getName()));
    }

    @Test
    public void janieHasSelectedRestaurant2() {
        InMemorySelectionQuery selectionQuery = new InMemorySelectionQuery();
        Restaurant restaurant = new Restaurant("r2", "loc");
        Workmate workmate = new Workmate("Janie");
        Selection selection = new Selection(restaurant, workmate);
        selectionQuery.setSelections(Arrays.asList(new Selection[]{selection}));

        WorkmateVO workmateVO = new SelectionInfoDecoratorForWorkMate(selectionQuery, new WorkmateVO(workmate))
                .decor();

        assert(workmateVO.getSelection().equals(restaurant));
    }
}
