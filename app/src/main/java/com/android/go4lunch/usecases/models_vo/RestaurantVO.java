package com.android.go4lunch.usecases.models_vo;

import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.enums.TimeInfo;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.usecases.enums.Vote;

import java.util.ArrayList;
import java.util.List;

public class RestaurantVO {

    private Restaurant restaurant;

    private TimeInfo timeInfo;

    private Long distanceInfo;

    private List<Workmate> selections;

    private Vote voteInfo;

    public RestaurantVO(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.selections = new ArrayList<>();
    }

    public RestaurantVO(Restaurant restaurant, TimeInfo timeInfo) {
        this(restaurant);
        this.timeInfo = timeInfo;
    }


    public RestaurantVO(Restaurant restaurant, TimeInfo timeInfo, Long distanceInfo) {
        this(restaurant, timeInfo);
        this.distanceInfo = distanceInfo;
    }

    public RestaurantVO(Restaurant restaurant, Long distanceInfo) {
        this(restaurant);
        this.distanceInfo = distanceInfo;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setTimeInfo(TimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }

    public TimeInfo getTimeInfo() {
        return this.timeInfo;
    }

    public void setDistanceInfo(Long distanceInfo) {
        this.distanceInfo = distanceInfo;
    }

    public Long getDistanceInfo() {
        return this.distanceInfo;
    }

    public void setSelections(List<Workmate> selections) {
        this.selections = selections;
    }

    public List<Workmate> getSelections() {
        return this.selections;
    }

    public int getSelectionCountInfo() {
        return this.selections.size();
    }

    public Vote getVoteInfo() {
        return voteInfo;
    }

    public void setVoteInfo(Vote voteInfo) {
        this.voteInfo = voteInfo;
    }
}
