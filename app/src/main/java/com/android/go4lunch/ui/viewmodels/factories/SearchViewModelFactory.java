package com.android.go4lunch.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.ui.viewmodels.SearchViewModel;

public class SearchViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel();
        } else {
            throw new IllegalArgumentException("SearchViewModelFactory: Unknown ViewModel class");
        }
    }
}
