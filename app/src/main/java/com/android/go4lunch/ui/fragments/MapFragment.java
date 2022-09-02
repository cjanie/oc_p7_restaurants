package com.android.go4lunch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.ui.viewmodels.Cache;
import com.android.go4lunch.ui.Mode;

public class MapFragment extends WithFrameLayoutFragment {

    private Cache cache;

    private MapRestaurantsNearbyFragment mapRestaurantsNearbyFragment;

    private MapSearchResultFragment mapSearchResultFragment;

    private MapSelectedRestaurantsFragment mapSelectedRestaurantsFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.cache = ((Launch)this.getActivity().getApplication()).cache();
        //this.cache = new ViewModelProvider(this).get(Cache.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        this.frameLayoutId = R.id.map_frame_layout;

        this.cache.getMode().observe(this.getViewLifecycleOwner(), mode -> {
            if(mode.equals(Mode.LIST)) {
                if(this.mapRestaurantsNearbyFragment == null) {
                    this.mapRestaurantsNearbyFragment = new MapRestaurantsNearbyFragment();
                }
                this.showFragment(this.mapRestaurantsNearbyFragment);
            }
            if(mode.equals(Mode.SEARCH)) {
                if(this.mapSearchResultFragment == null) {
                    this.mapSearchResultFragment = new MapSearchResultFragment();
                }
                this.showFragment(this.mapSearchResultFragment);
            }
            if(mode.equals(Mode.FILTER_SELECTIONS)) {
                if(this.mapSelectedRestaurantsFragment == null) {
                    this.mapSelectedRestaurantsFragment = new MapSelectedRestaurantsFragment();
                }
                this.showFragment(this.mapSelectedRestaurantsFragment);
            }
        });
        return root;
    }



}
