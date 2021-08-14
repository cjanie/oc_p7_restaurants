package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.enums.TimeInfo;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.enums.Vote;

public class RestaurantVO {

    private Restaurant restaurant;

    private TimeInfo timeInfo;

    private Long distanceInfo;

    private int selectionCountInfo;

    private Vote voteInfo;

    public RestaurantVO(Restaurant restaurant) {
        this.restaurant = restaurant;
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

    public void setSelectionsCountInfo(int selectionCountInfo) {
        this.selectionCountInfo = selectionCountInfo;
    }

    public int getSelectionCountInfo() {
        return selectionCountInfo;
    }

    public void setSelectionCountInfo(int selectionCountInfo) {
        this.selectionCountInfo = selectionCountInfo;
    }

    public Vote getVoteInfo() {
        return voteInfo;
    }

    public void setVoteInfo(Vote voteInfo) {
        this.voteInfo = voteInfo;
    }
}
