package com.android.go4lunch.usecases;

import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.repositories.InMemoryHistoricOfSelectionsRepository;
import com.android.go4lunch.usecases.decorators.VoteResult;
import com.android.go4lunch.usecases.enums.Vote;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteResultTest {

    private Restaurant restaurant;

    @Before
    public void setUp() {
        this.restaurant = new Restaurant("Oa", "loc");
    }

    private void assertMatchesData(int selectionsCount, Vote expected) {
        InMemoryHistoricOfSelectionsRepository inMemoryHistoricOfSelectionsRepository = new InMemoryHistoricOfSelectionsRepository();
        List<Map<Restaurant, Integer>> list = new ArrayList<>();
        Map<Restaurant, Integer> map = new HashMap<>();
        map.put(this.restaurant, selectionsCount);
        list.add(map);
        inMemoryHistoricOfSelectionsRepository.setList(list);
        assert(new VoteResult(inMemoryHistoricOfSelectionsRepository).get(this.restaurant).equals(expected));
    }
    @Test
    public void shouldReturnOneStarAccordingToVoteRule() {
        this.assertMatchesData(Vote.ONE_STAR.getSelectionsCount(), Vote.ONE_STAR);
    }

    @Test
    public void shouldReturnTwoStarsAccordingToVoteRule() {
        int twoStarRule = Vote.TWO_STARS.getSelectionsCount();
        int currentSelectionsCount = twoStarRule;
        this.assertMatchesData(currentSelectionsCount, Vote.TWO_STARS);
    }

    @Test
    public void shouldReturnThreeStarsAccordingToVoteRule() {
        int twoStarRule = Vote.THREE_STARS.getSelectionsCount();
        int currentSelectionsCount = twoStarRule;
        this.assertMatchesData(currentSelectionsCount, Vote.THREE_STARS);
    }

    @Test
    public void shouldReturnNoStarBeforeReachingOneStarRule() {
        int oneStarRule = Vote.ONE_STAR.getSelectionsCount();
        int currentSelectionsCount = oneStarRule - 1;
        this.assertMatchesData(currentSelectionsCount, Vote.MINIMUM);
    }

    @Test
    public void shouldKeepReturnOneStarBeforeReachingTwoStarsRule() {
        int twoStarsRule = Vote.TWO_STARS.getSelectionsCount();
        int currentSelectionCount = twoStarsRule - 1;
        this.assertMatchesData(currentSelectionCount, Vote.ONE_STAR);

    }

    @Test
    public void shouldKeepReturnTwoStarsBeforeReachingThreeStarsRule() {
        int threeStarsRule = Vote.THREE_STARS.getSelectionsCount();
        int currentSelectionCount = threeStarsRule - 1;
        this.assertMatchesData(currentSelectionCount, Vote.TWO_STARS);
    }

    @Test
    public void shouldKeepReturnThreeStarsWhenHighterThanThreeStarsRule() {
        int threeStarsRule = Vote.THREE_STARS.getSelectionsCount();
        int currentSelectionsCount = threeStarsRule + 1;
        this.assertMatchesData(currentSelectionsCount, Vote.THREE_STARS);
    }

}
