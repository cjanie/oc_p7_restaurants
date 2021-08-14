package com.android.go4lunch.write.businesslogic.usecases;


import com.android.go4lunch.write.businesslogic.usecases.enums.VoteRule;

public class Vote {

    private Counter counter;

    public Vote(Counter counter) {
        this.counter = counter;
    }

    public VoteRule vote() {
        VoteRule voteRule = VoteRule.MINIMUM;

        if(counter.countSelections() >= VoteRule.ONE_STAR.getSelectionsCount()) {
            if(counter.countSelections() < VoteRule.TWO_STARS.getSelectionsCount()) {
                voteRule = VoteRule.ONE_STAR;
            }
        }
        if(counter.countSelections() >= VoteRule.TWO_STARS.getSelectionsCount())
            if(counter.countSelections() < VoteRule.THREE_STARS.getSelectionsCount()) {
                voteRule = VoteRule.TWO_STARS;
            }
        if(counter.countSelections() >= VoteRule.THREE_STARS.getSelectionsCount())
            voteRule = VoteRule.THREE_STARS;
        return voteRule;


    }


}
