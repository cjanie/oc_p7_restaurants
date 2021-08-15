package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.Decorator;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.enums.Vote;

public class VoteInfoDecorator implements Decorator<RestaurantVO> {

    private VoteResult voteResult;

    public VoteInfoDecorator(VoteResult voteResult) {
        this.voteResult = voteResult;
    }

    @Override
    public RestaurantVO decor(RestaurantVO restaurant) {
        Vote vote = this.voteResult.get();
        restaurant.setVoteInfo(vote);
        return restaurant;
    }
}
