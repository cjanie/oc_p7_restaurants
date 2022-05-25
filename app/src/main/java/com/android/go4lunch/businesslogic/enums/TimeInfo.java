package com.android.go4lunch.businesslogic.enums;

public enum TimeInfo {
    OPEN {
        @Override
        public <E> E accept(TimeInfoVisitor<E> visitor) {
            return visitor.visitOpen();
        }
    },
    CLOSE {
        @Override
        public <E> E accept(TimeInfoVisitor<E> visitor) {
            return visitor.visitClose();
        }
    },
    CLOSING_SOON {
        @Override
        public <E> E accept(TimeInfoVisitor<E> visitor) {
            return visitor.visitClosingSoon();
        }
    },
    DEFAULT_TIME_INFO {
        @Override
        public <E> E accept(TimeInfoVisitor<E> visitor) {
            return visitor.visitDefaultTimeInfo();
        }
    };

    public abstract <E> E accept(TimeInfoVisitor<E> visitor);

}
