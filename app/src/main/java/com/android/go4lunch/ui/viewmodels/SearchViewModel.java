package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> id;

    public SearchViewModel() {
        this.id = new MutableLiveData<>();
    }

    public void setId(String id) {
        this.id.setValue(id);
    }

    public LiveData<String> getId() {
        return this.id;
    }
}
