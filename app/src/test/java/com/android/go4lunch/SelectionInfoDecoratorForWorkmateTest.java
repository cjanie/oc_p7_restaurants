package com.android.go4lunch;

import com.android.go4lunch.InMemoryHistoricOfSelectionsRepository;
import com.android.go4lunch.InMemoryCurrentSelectionsRepository;
import com.android.go4lunch.usecases.decorators.SelectionInfoDecoratorForWorkMate;
import com.android.go4lunch.usecases.models_vo.WorkmateVO;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SelectionInfoDecoratorForWorkmateTest {
/*
    @Test
    public void janieHasSelectedRestaurant1() {
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemoryCurrentSelectionsRepository selectionQuery = new InMemoryCurrentSelectionsRepository();
        Restaurant restaurant = new Restaurant("r1", "loc");
        Workmate workmate = new Workmate("Janie");
        Selection selection = new Selection(restaurant, workmate);
        selectionQuery.setSelections(Arrays.asList(new Selection[]{selection}));

        WorkmateVO workmateVO = new SelectionInfoDecoratorForWorkMate(selectionQuery)
                .decor(new WorkmateVO(workmate));

        assert(workmateVO.getSelection().getName().equals(restaurant.getName()));
    }

    @Test
    public void janieHasSelectedRestaurant2() {
        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        InMemoryCurrentSelectionsRepository selectionQuery = new InMemoryCurrentSelectionsRepository();
        Restaurant restaurant = new Restaurant("r2", "loc");
        Workmate workmate = new Workmate("Janie");
        Selection selection = new Selection(restaurant, workmate);
        selectionQuery.setSelections(Arrays.asList(new Selection[]{selection}));

        WorkmateVO workmateVO = new SelectionInfoDecoratorForWorkMate(selectionQuery)
                .decor(new WorkmateVO(workmate));

        assert(workmateVO.getSelection().equals(restaurant));
    }

 */
}
