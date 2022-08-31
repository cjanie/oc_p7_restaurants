package com.android.go4lunch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.ui.Cache;
import com.android.go4lunch.ui.Mode;
import com.android.go4lunch.ui.viewmodels.SharedViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MapFragment extends WithFrameLayoutFragment {

    private Cache cache;

    private MapRestaurantFragment mapRestaurantFragment;

    private MapSearchResultFragment mapSearchResultFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.cache = ((Launch)this.getActivity().getApplication()).cache();
        //this.cache = new ViewModelProvider(this).get(Cache.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        this.frameLayoutId = R.id.map_frame_layout;

        this.cache.getMode().observe(this.getViewLifecycleOwner(), mode -> {
            if(mode.equals(Mode.LIST)) {
                if(this.mapRestaurantFragment == null) {
                    this.mapRestaurantFragment = new MapRestaurantFragment();
                }
                this.showFragment(this.mapRestaurantFragment);
            }
            if(mode.equals(Mode.SEARCH)) {
                if(this.mapSearchResultFragment == null) {
                    this.mapSearchResultFragment = new MapSearchResultFragment();
                }
                this.showFragment(this.mapSearchResultFragment);
            }
        });
        return root;
    }



}
