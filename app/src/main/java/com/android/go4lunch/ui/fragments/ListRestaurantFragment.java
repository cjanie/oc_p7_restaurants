package com.android.go4lunch.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.ui.adapters.ListRestaurantRecyclerViewAdapter;
import com.android.go4lunch.ui.viewmodels.RestaurantsViewModel;
import com.android.go4lunch.ui.viewmodels.SharedViewModel;

import java.util.ArrayList;

public class ListRestaurantFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    private RestaurantsViewModel restaurantsViewModel;

    RecyclerView recyclerView;

    ListRestaurantRecyclerViewAdapter adapter;

    public ListRestaurantFragment(SharedViewModel sharedViewModel) {
        this.sharedViewModel = sharedViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Data
        this.restaurantsViewModel = new ViewModelProvider(
                this,
                ((Launch) this.getActivity().getApplication()).restaurantsViewModelFactory()
        ).get(RestaurantsViewModel.class);

        // UI
        View root = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        Context context = root.getContext();
        this.recyclerView = (RecyclerView) root;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        // TODO Creer adaper. Le parametrer pour afficher le loader. ou dans constructeur par défault de l'adapter
        this.adapter = new ListRestaurantRecyclerViewAdapter();

        // Listens to the result of the view model action
        this.observeRestaurantsData();

        // Call the View model action
        this.updateRestaurantsDataFromMyPosition();

        return root;
    }

    private void observeRestaurantsData() {
        this.restaurantsViewModel.getRestaurantsLiveData().observe(this.getViewLifecycleOwner(), restaurants -> {
            this.adapter.updateList(restaurants);
            this.recyclerView.setAdapter(adapter);
        });
    }

    private void updateRestaurantsDataFromMyPosition() {
        this.sharedViewModel.getGeolocation().observe(this.getViewLifecycleOwner(), geolocation -> {
            if(geolocation != null) {
                this.restaurantsViewModel.fetchRestaurantsObservableToUpdateLiveData(
                        geolocation.getLatitude(),
                        geolocation.getLongitude(),
                        1000
                );

            }
        });
    }

}
