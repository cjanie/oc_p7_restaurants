package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.usecases.SearchRestaurantByIdUseCase;

public class SearchViewModel extends ViewModel {

    private SearchRestaurantByIdUseCase searchUseCase;

    private MutableLiveData<Restaurant> foundLiveData;

    public SearchViewModel(SearchRestaurantByIdUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
        this.foundLiveData = new MutableLiveData<>();
    }

    public void find(String id) {
        this.searchUseCase.handle(id).subscribe(restaurant -> foundLiveData.postValue(restaurant));
    }

    public LiveData<Restaurant> getFound(String id) {
        return this.foundLiveData;
    }
}
