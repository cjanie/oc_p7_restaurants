package com.android.go4lunch.businesslogic.enums;

public enum Vote {
    MINIMUM(0),
    ONE_STAR(2),
    TWO_STARS(4),
    THREE_STARS(6);

    private final int selectionsCount;

    Vote(final int selectionsCount) {
        this.selectionsCount = selectionsCount;
    }

    public int getSelectionsCount() {
        return this.selectionsCount;
    }
}
