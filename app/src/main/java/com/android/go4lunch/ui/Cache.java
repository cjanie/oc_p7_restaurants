package com.android.go4lunch.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Geolocation;

public class Cache extends ViewModel {

    private MutableLiveData<Geolocation> myPosition;

    private MutableLiveData<Mode> mode;

    private MutableLiveData<String> restaurantIdForSearch;

    public Cache() {
        this.init();
    }

    public void setMyPosition(Geolocation myPosition) {
        this.myPosition.setValue(myPosition);
    }

    public LiveData<Geolocation> getMyPosition() {
        return this.myPosition;
    }

    public void setMode(Mode mode) {
        this.mode.postValue(mode);
    }

    public LiveData<Mode> getMode() {
        return this.mode;
    }

    public void setRestaurantIdForSearch(String restaurantId) {
        this.restaurantIdForSearch.postValue(restaurantId);
    }

    public LiveData<String> getRestaurantIdForSearch() {
        return this.restaurantIdForSearch;
    }

    private void clear() {
        this.init();
    }

    private void init() {
        this.myPosition = new MutableLiveData<>();
        this.mode = new MutableLiveData<>(Mode.LIST);
        this.restaurantIdForSearch = new MutableLiveData<>();
    }
}
