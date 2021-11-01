package com.android.go4lunch.usecases.decorators;

import com.android.go4lunch.usecases.models_vo.RestaurantVO;
import com.android.go4lunch.usecases.enums.Vote;

public class VoteInfoDecorator {

    private VoteResult voteResult;

    public VoteInfoDecorator(VoteResult voteResult) {
        this.voteResult = voteResult;
    }

    public RestaurantVO decor(RestaurantVO restaurant) {
        Vote vote = this.voteResult.get(restaurant.getRestaurant());
        restaurant.setVoteInfo(vote);
        return restaurant;
    }
}
