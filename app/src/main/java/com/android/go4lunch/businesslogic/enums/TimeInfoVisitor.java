package com.android.go4lunch.businesslogic.enums;

public interface TimeInfoVisitor<T> {
    T visitOpen();
    T visitClose();
    T visitClosingSoon();
    T visitDefaultTimeInfo();
}
