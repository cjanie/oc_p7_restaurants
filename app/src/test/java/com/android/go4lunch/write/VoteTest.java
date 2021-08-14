package com.android.go4lunch.write;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.write.businesslogic.usecases.Counter;
import com.android.go4lunch.write.businesslogic.usecases.InMemoryCounter;
import com.android.go4lunch.write.businesslogic.usecases.Vote;
import com.android.go4lunch.write.businesslogic.usecases.enums.VoteRule;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteTest {

    private Restaurant restaurant;

    @Before
    public void setUp() {
        this.restaurant = new Restaurant("Oa", "loc");
    }

    private void assertMatchesData(int selectionsCount, VoteRule expected) {
        InMemoryCounter inMemoryCounter = new InMemoryCounter();
        List<Map<Restaurant, Integer>> list = new ArrayList<>();
        Map<Restaurant, Integer> map = new HashMap<>();
        map.put(this.restaurant, selectionsCount);
        list.add(map);
        inMemoryCounter.setList(list);
        Counter counter = new Counter(this.restaurant, inMemoryCounter);
        assert(new Vote(counter).vote().equals(expected));
    }
    @Test
    public void shouldReturnOneStarAccordingToVoteRule() {
        this.assertMatchesData(VoteRule.ONE_STAR.getSelectionsCount(), VoteRule.ONE_STAR);
    }

    @Test
    public void shouldReturnTwoStarsAccordingToVoteRule() {
        int twoStarRule = VoteRule.TWO_STARS.getSelectionsCount();
        int currentSelectionsCount = twoStarRule;
        this.assertMatchesData(currentSelectionsCount, VoteRule.TWO_STARS);
    }

    @Test
    public void shouldReturnThreeStarsAccordingToVoteRule() {
        int twoStarRule = VoteRule.THREE_STARS.getSelectionsCount();
        int currentSelectionsCount = twoStarRule;
        this.assertMatchesData(currentSelectionsCount, VoteRule.THREE_STARS);
    }

    @Test
    public void shouldReturnNoStarBeforeReachingOneStarRule() {
        int oneStarRule = VoteRule.ONE_STAR.getSelectionsCount();
        int currentSelectionsCount = oneStarRule - 1;
        this.assertMatchesData(currentSelectionsCount, VoteRule.MINIMUM);
    }

    @Test
    public void shouldKeepReturnOneStarBeforeReachingTwoStarsRule() {
        int twoStarsRule = VoteRule.TWO_STARS.getSelectionsCount();
        int currentSelectionCount = twoStarsRule - 1;
        this.assertMatchesData(currentSelectionCount, VoteRule.ONE_STAR);

    }

    @Test
    public void shouldKeepReturnTwoStarsBeforeReachingThreeStarsRule() {
        int threeStarsRule = VoteRule.THREE_STARS.getSelectionsCount();
        int currentSelectionCount = threeStarsRule - 1;
        this.assertMatchesData(currentSelectionCount, VoteRule.TWO_STARS);
    }

    @Test
    public void shouldKeepReturnThreeStarsWhenHighterThanThreeStarsRule() {
        int threeStarsRule = VoteRule.THREE_STARS.getSelectionsCount();
        int currentSelectionsCount = threeStarsRule + 1;
        this.assertMatchesData(currentSelectionsCount, VoteRule.THREE_STARS);
    }

}
