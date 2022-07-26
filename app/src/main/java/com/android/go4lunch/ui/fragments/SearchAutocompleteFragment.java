package com.android.go4lunch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.Launch;
import com.android.go4lunch.data.apiGoogleMaps.repositories.GoogleMapsRequestConfig;
import com.android.go4lunch.ui.Cache;
import com.android.go4lunch.ui.Mode;
import com.android.go4lunch.ui.viewmodels.SearchViewModel;
import com.android.go4lunch.ui.viewmodels.factories.SearchViewModelFactory;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class SearchAutocompleteFragment extends AutocompleteSupportFragment {
    // https://developers.google.com/maps/documentation/places/android-sdk/autocomplete#select-place-details
    // Text watcher: https://rrtutors.com/tutorials/android-auto-suggesion-with-textwatcher-example



    private Cache cache;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.cache = ((Launch)this.getActivity().getApplication()).cache();

        this.setTypeFilter(TypeFilter.ESTABLISHMENT);
        Places.initialize(this.getActivity().getApplicationContext(), GoogleMapsRequestConfig.API_KEY);

        this.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        this.setLocationRestriction(RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596)));
        this.setTypeFilter(TypeFilter.ESTABLISHMENT);

        this.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(SearchAutocompleteFragment.this.getActivity(), "error on selected place listener: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // name and id
                cache.setMode(Mode.SEARCH);
                cache.setRestaurantIdForSearch(place.getId());
            }
        });



        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
