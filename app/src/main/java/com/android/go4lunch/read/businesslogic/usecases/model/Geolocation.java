package com.android.go4lunch.read.businesslogic.usecases.model;

public class Geolocation {

    private Double x;
    private Double y;

    public Geolocation(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}
