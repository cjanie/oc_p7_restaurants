package com.android.go4lunch.read.businesslogic.usecases.decorators;


import com.android.go4lunch.read.businesslogic.usecases.RetrieveSelectionsCountForOneRestaurant;
import com.android.go4lunch.read.businesslogic.usecases.enums.Vote;

public class VoteResult {

    private RetrieveSelectionsCountForOneRestaurant retrieveSelectionsCountForOneRestaurant;

    public VoteResult(RetrieveSelectionsCountForOneRestaurant retrieveSelectionsCountForOneRestaurant) {
        this.retrieveSelectionsCountForOneRestaurant = retrieveSelectionsCountForOneRestaurant;
    }

    public Vote get() {
        Vote vote = Vote.MINIMUM;

        if(retrieveSelectionsCountForOneRestaurant.countSelections() >= Vote.ONE_STAR.getSelectionsCount()) {
            if(retrieveSelectionsCountForOneRestaurant.countSelections() < Vote.TWO_STARS.getSelectionsCount()) {
                vote = Vote.ONE_STAR;
            }
        }
        if(retrieveSelectionsCountForOneRestaurant.countSelections() >= Vote.TWO_STARS.getSelectionsCount())
            if(retrieveSelectionsCountForOneRestaurant.countSelections() < Vote.THREE_STARS.getSelectionsCount()) {
                vote = Vote.TWO_STARS;
            }
        if(retrieveSelectionsCountForOneRestaurant.countSelections() >= Vote.THREE_STARS.getSelectionsCount())
            vote = Vote.THREE_STARS;
        return vote;


    }


}
