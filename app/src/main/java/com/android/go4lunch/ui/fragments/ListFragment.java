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

public class ListFragment extends WithFrameLayoutFragment {

    private Cache cache;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.cache = ((Launch)this.getActivity().getApplication()).cache();
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        this.frameLayoutId = R.id.list_frame_layout;

        this.cache.getMode().observe(this.getViewLifecycleOwner(), mode -> {

            if(mode.equals(Mode.LIST)) {
                this.showFragment(new ListRestaurantFragment());
            }
            if(mode.equals(Mode.SEARCH)) {
                this.showFragment(new ListSearchResultFragment());

            }
        });
        return root;
    }

}
