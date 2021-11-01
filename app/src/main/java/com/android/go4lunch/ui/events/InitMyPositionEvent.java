package com.android.go4lunch.ui.events;

public class InitMyPositionEvent {

    private Double latitude;

    private Double longitude;

    public InitMyPositionEvent(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
