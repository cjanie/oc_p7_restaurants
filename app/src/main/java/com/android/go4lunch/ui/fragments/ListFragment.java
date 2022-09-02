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

public class ListFragment extends WithFrameLayoutFragment {

    private Cache cache;

    private ListRestaurantsNearbyFragment listRestaurantsNearbyFragment;

    private ListSearchResultFragment listSearchResultFragment;

    private ListSelectedRestaurantsFragment listSelectedRestaurantsFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.cache = ((Launch)this.getActivity().getApplication()).cache();
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        this.frameLayoutId = R.id.list_frame_layout;

        this.cache.getMode().observe(this.getViewLifecycleOwner(), mode -> {

            if(mode.equals(Mode.LIST)) {
                if(this.listRestaurantsNearbyFragment == null) {
                    this.listRestaurantsNearbyFragment = new ListRestaurantsNearbyFragment();
                }
                this.showFragment(this.listRestaurantsNearbyFragment);
            }
            if(mode.equals(Mode.SEARCH)) {
                if(this.listSearchResultFragment == null) {
                    this.listSearchResultFragment = new ListSearchResultFragment();
                }
                this.showFragment(this.listSearchResultFragment);
            }
            if(mode.equals(Mode.FILTER_SELECTIONS)) {
                if(this.listSelectedRestaurantsFragment == null) {
                    this.listSelectedRestaurantsFragment = new ListSelectedRestaurantsFragment();
                }
                this.showFragment(this.listSelectedRestaurantsFragment);
            }
        });

        return root;
    }

}
