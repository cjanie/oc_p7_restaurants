package com.android.go4lunch.usecases;

import com.android.go4lunch.repositories.InMemoryCurrentSelectionsRepository;
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

    private InMemoryCurrentSelectionsRepository selectionQuery;

    private Restaurant restaurant;

    @Before
    public void setUp() {
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

    private SelectionInfoDecoratorForRestaurant createDecorator(List<Selection> selections) {
        InMemoryCurrentSelectionsRepository inMemoryCurrentSelectionsRepository = new InMemoryCurrentSelectionsRepository();
        inMemoryCurrentSelectionsRepository.setSelections(selections);
        return new SelectionInfoDecoratorForRestaurant(inMemoryCurrentSelectionsRepository);
    }

    private void assertMatchesSelectionCount(int expectedCount) {
       //assert(new SelectionInfoDecoratorForRestaurant(this.selectionQuery)
               // .decor(new RestaurantVO(this.restaurant)).getSelectionCountInfo() == expectedCount);
    }

    @Test
    public void shouldReturn1IfRestaurantHas1Selection() {
        List<Selection> selections = Arrays.asList(
                new Selection(new Restaurant("uuid","oooBonGoûter", "alifon street"),
                        new Workmate("Janie"))
        );
        // SUT
        Observable<RestaurantVO> actual = this.createDecorator(selections)
                .decor(new RestaurantVO(new Restaurant("uuid","oooBonGoûter", "alifon street")));
        List<RestaurantVO> results = new ArrayList<>();
        actual.subscribe(results::add);
        // assertion
        assert(results.get(0).getSelectionCountInfo() == 1);
    }

    @Test
    public void shouldReturn2IfRestaurantHas2Selections() {
        List<Selection> selections = Arrays.asList(
                new Selection(new Restaurant("uuid","oooBonGoûter", "alifon street"),
                        new Workmate("Janie")),
                new Selection(new Restaurant("uuid","oooBonGoûter", "alifon street"),
                        new Workmate("Cyril"))
        );
        // SUT
        Observable<RestaurantVO> actual = this.createDecorator(selections)
                .decor(new RestaurantVO(new Restaurant("uuid","oooBonGoûter", "alifon street")));
        List<RestaurantVO> results = new ArrayList<>();
        actual.subscribe(results::add);
        // assertion
        assert(results.get(0).getSelectionCountInfo() == 2);
    }

    @Test
    public void shouldReturn3IfRestaurantHas3Selections() {
        List<Selection> selections = Arrays.asList(
                new Selection(new Restaurant("uuid","oooBonGoûter", "alifon street"),
                        new Workmate("Janie")),
                new Selection(new Restaurant("uuid","oooBonGoûter", "alifon street"),
                        new Workmate("Cyril")),
                new Selection(new Restaurant("uuid","oooBonGoûter", "alifon street"),
                        new Workmate("Olivier"))
        );
        // SUT
        Observable<RestaurantVO> actual = this.createDecorator(selections)
                .decor(new RestaurantVO(new Restaurant("uuid","oooBonGoûter", "alifon street")));
        List<RestaurantVO> results = new ArrayList<>();
        actual.subscribe(results::add);
        // assertion
        assert(results.get(0).getSelectionCountInfo() == 3);
    }

    @Test
    public void shouldReturn0IfRestaurantIsNotTheOneSelected() {
        // SUT
        Observable<RestaurantVO> actual = this.createDecorator(new ArrayList<>())
                .decor(new RestaurantVO(new Restaurant("uuid","oooBonGoûter", "alifon street")));
        List<RestaurantVO> results = new ArrayList<>();
        actual.subscribe(results::add);
        // assertion
        assert(results.get(0).getSelectionCountInfo() == 0);
    }

    @Test
    public void shouldReturn2IfRestaurantHas2SelectionsAmong3Selections() {
        List<Selection> selections = Arrays.asList(
                new Selection(new Restaurant("uuid","oooBonGoûter", "alifon street"),
                        new Workmate("Janie")),
                new Selection(new Restaurant("uuid","oooBonGoûter", "alifon street"),
                        new Workmate("Cyril")),
                new Selection(new Restaurant("uuid_2","another selected restaurant", "alifon street"),
                        new Workmate("Olivier"))
        );
        // SUT
        Observable<RestaurantVO> actual = this.createDecorator(selections)
                .decor(new RestaurantVO(new Restaurant("uuid","oooBonGoûter", "alifon street")));
        List<RestaurantVO> results = new ArrayList<>();
        actual.subscribe(results::add);
        // assertion
        assert(results.get(0).getSelectionCountInfo() == 2);
    }
}
