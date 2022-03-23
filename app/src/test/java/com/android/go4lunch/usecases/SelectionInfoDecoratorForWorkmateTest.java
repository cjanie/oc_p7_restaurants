package com.android.go4lunch.usecases;

import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.decorators.SelectionInfoDecoratorForWorkMate;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SelectionInfoDecoratorForWorkmateTest {

    @Test
    public void workmateHasSelectedARestaurant() {
        Workmate workmate1 = new Workmate("Workmate1");
        workmate1.setId("1");

        Selection selection = new Selection(
                "1", "resto1",
                "1", "workmate1"
                );
        SelectionInfoDecoratorForWorkMate decorator = new SelectionInfoDecoratorForWorkMate(Arrays.asList(selection));

        assert(decorator.decor(workmate1).getSelection().getName().equals("resto1"));
    }

    @Test
    public void workmateHasNoSelection() {
        Workmate workmate1 = new Workmate("Workmate1");
        workmate1.setId("1");

        SelectionInfoDecoratorForWorkMate decorator = new SelectionInfoDecoratorForWorkMate(Arrays.asList());

        assertNull(decorator.decor(workmate1).getSelection());
    }
}
