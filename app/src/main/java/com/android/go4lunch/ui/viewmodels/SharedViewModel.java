package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.models.Geolocation;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Geolocation> geolocation ;

    public SharedViewModel() {
        this.geolocation = new MutableLiveData<>();
    }

    public LiveData<Geolocation> getGeolocation() {
        return this.geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation.setValue(geolocation);
    }
}
