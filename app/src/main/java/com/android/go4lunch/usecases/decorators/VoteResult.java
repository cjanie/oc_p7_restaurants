package com.android.go4lunch.usecases.decorators;


import com.android.go4lunch.gateways.HistoricOfSelectionsGateway;
import com.android.go4lunch.usecases.enums.Vote;
import com.android.go4lunch.models.Restaurant;

public class VoteResult {

    private HistoricOfSelectionsGateway historicOfSelectionsGateway;

    public VoteResult(HistoricOfSelectionsGateway historicOfSelectionsGateway) {
        this.historicOfSelectionsGateway = historicOfSelectionsGateway;
    }

    public Vote get(Restaurant restaurant) {
        Vote vote = Vote.MINIMUM;
/*
        if(this.countSelections(restaurant) >= Vote.ONE_STAR.getSelectionsCount()) {
            if(this.countSelections(restaurant) < Vote.TWO_STARS.getSelectionsCount()) {
                vote = Vote.ONE_STAR;
            }
        }
        if(this.countSelections(restaurant) >= Vote.TWO_STARS.getSelectionsCount())
            if(this.countSelections(restaurant) < Vote.THREE_STARS.getSelectionsCount()) {
                vote = Vote.TWO_STARS;
            }
        if(this.countSelections(restaurant) >= Vote.THREE_STARS.getSelectionsCount())
            vote = Vote.THREE_STARS;

 */
        return vote;
    }
/*
    private int countSelections(Restaurant restaurant) {
        return this.historicOfSelectionsQuery.getCount(restaurant);
    }

*/
}
