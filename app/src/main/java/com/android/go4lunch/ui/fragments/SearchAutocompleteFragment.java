package com.android.go4lunch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.go4lunch.Launch;
import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.data.apiGoogleMaps.repositories.GoogleMapsRequestConfig;
import com.android.go4lunch.ui.Cache;
import com.android.go4lunch.ui.Mode;
import com.android.go4lunch.ui.utils.RectangularBoundsFactory;
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

        Places.initialize(this.getActivity().getApplicationContext(), GoogleMapsRequestConfig.API_KEY);

        this.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

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

        this.cache.getMyPosition().observe(this.getViewLifecycleOwner(), myPosition -> {
            Double forkOfLatitudesAndLongitudes = 0.012;
            RectangularBounds rectangularBounds = new RectangularBoundsFactory(myPosition, forkOfLatitudesAndLongitudes).create();

            this.setLocationRestriction(rectangularBounds);
        });


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
