package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.usecases.GetMyPositionUseCase;
import com.android.go4lunch.businesslogic.usecases.SaveMyPositionUseCase;

public class SharedViewModel extends ViewModel {

    private final SaveMyPositionUseCase saveMyPositionUseCase;

    private final GetMyPositionUseCase getMyPositionUseCase;

    private final MutableLiveData<Geolocation> geolocation ;

    public SharedViewModel(
            SaveMyPositionUseCase saveMyPositionUseCase,
            GetMyPositionUseCase getMyPositionUseCase) {
        this.geolocation = new MutableLiveData<>();
        this.saveMyPositionUseCase = saveMyPositionUseCase;
        this.getMyPositionUseCase = getMyPositionUseCase;
    }

    public LiveData<Geolocation> getGeolocation() {
        return this.geolocation;
    }

    private void updateGeolocationLiveData() {
        this.geolocation.setValue(this.getMyPositionUseCase.handle());

    }

    public void saveMyPosition(Geolocation myPosition) {
        this.saveMyPositionUseCase.handle(myPosition.getLatitude(), myPosition.getLongitude());
        this.updateGeolocationLiveData();
    }
}
