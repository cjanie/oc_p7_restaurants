package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.Decorator;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.enums.Vote;

public class VoteInfoDecorator implements Decorator {

    private RestaurantVO restaurant;

    private VoteResult voteResult;

    public VoteInfoDecorator(RestaurantVO restaurant, VoteResult voteResult) {
        this.restaurant = restaurant;
        this.voteResult = voteResult;
    }

    @Override
    public RestaurantVO decor() {
        Vote vote = this.voteResult.get();
        this.restaurant.setVoteInfo(vote);
        return this.restaurant;
    }
}
