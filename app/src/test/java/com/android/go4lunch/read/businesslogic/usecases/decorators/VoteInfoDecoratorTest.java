package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.InMemoryHistoricOfSelectionsRepository;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.VoteResult;
import com.android.go4lunch.read.businesslogic.usecases.enums.Vote;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteInfoDecoratorTest {

    private void assertMatchesVoteRules(int selectionsCount, Vote expected) {
        RestaurantVO restaurantVO = new RestaurantVO(new Restaurant("AA", "LOC"));
        InMemoryHistoricOfSelectionsRepository inMemoryHistoricOfSelectionsRepository = new InMemoryHistoricOfSelectionsRepository();
        List<Map<Restaurant, Integer>> list = new ArrayList<>();
        Map<Restaurant, Integer> map = new HashMap<>();
        map.put(restaurantVO.getRestaurant(), selectionsCount);
        list.add(map);
        inMemoryHistoricOfSelectionsRepository.setList(list);

        VoteResult voteResult = new VoteResult(inMemoryHistoricOfSelectionsRepository);
        restaurantVO = new VoteInfoDecorator(voteResult).decor(restaurantVO);

        assert(restaurantVO.getVoteInfo().equals(expected));
    }

    @Test
    public void shouldReturnVoteMinimumWhenRestaurantHasNoSelection() {

        RestaurantVO restaurantVO = new RestaurantVO(new Restaurant("AA", "LOC"));
        InMemoryHistoricOfSelectionsRepository inMemoryHistoricOfSelectionsRepository = new InMemoryHistoricOfSelectionsRepository();
        VoteResult voteResult = new VoteResult(inMemoryHistoricOfSelectionsRepository);
        restaurantVO = new VoteInfoDecorator(voteResult).decor(restaurantVO);
        assert(restaurantVO.getVoteInfo().equals(Vote.MINIMUM));
    }

    @Test
    public void shouldReturnOneStarAccordingToVoteRule() {
        this.assertMatchesVoteRules(Vote.ONE_STAR.getSelectionsCount(), Vote.ONE_STAR);
    }

    @Test
    public void shouldReturnTwoStarsAccordingToVoteRule() {
        this.assertMatchesVoteRules(Vote.TWO_STARS.getSelectionsCount(), Vote.TWO_STARS);
    }

    @Test
    public void shouldReturnThreeStarsAccordingToVoteRule() {
        this.assertMatchesVoteRules(Vote.THREE_STARS.getSelectionsCount(), Vote.THREE_STARS);
    }

    @Test
    public void shouldKeepReturnOneStarBeforeReachingTwoStarsRule() {
        int currentSelectionsCount = Vote.TWO_STARS.getSelectionsCount() - 1;
        this.assertMatchesVoteRules(currentSelectionsCount, Vote.ONE_STAR);
    }

    @Test
    public void shouldKeepReturnTwoStarsBeforeReachingThreeStarsRule() {
        int currentSelectionsCount = Vote.THREE_STARS.getSelectionsCount() - 1;
        this.assertMatchesVoteRules(currentSelectionsCount, Vote.TWO_STARS);
    }

    @Test
    public void shouldKeepReturnThreeStarsAfterReachingThreeStarsRule() {
        int currentSelectionsCount = Vote.THREE_STARS.getSelectionsCount() + 10;
        this.assertMatchesVoteRules(currentSelectionsCount, Vote.THREE_STARS);
    }
}
