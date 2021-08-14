package com.android.go4lunch.write.businesslogic.usecases.enums;

public enum VoteRule {
    MINIMUM(0),
    ONE_STAR(2),
    TWO_STARS(4),
    THREE_STARS(6);

    private final int selectionsCount;

    VoteRule(final int selectionsCount) {
        this.selectionsCount = selectionsCount;
    }

    public int getSelectionsCount() {
        return this.selectionsCount;
    }
}
