package com.android.go4lunch.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.ui.adapters.ListRestaurantRecyclerViewAdapter;
import com.android.go4lunch.ui.viewmodels.Cache;
import com.android.go4lunch.ui.viewmodels.SelectedRestaurantsViewModel;
import com.android.go4lunch.ui.viewmodels.factories.SelectedRestaurantsViewModelFactory;

public class ListSelectedRestaurantsFragment extends Fragment {

    private SelectedRestaurantsViewModel selectedRestaurantsViewModel;

    private Cache cache;

    private RecyclerView recyclerView;

    private ListRestaurantRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SelectedRestaurantsViewModelFactory viewModelFactory = ((Launch)this.getActivity().getApplication()).selectedRestaurantsViewModelFactory();
        this.selectedRestaurantsViewModel = new ViewModelProvider(this, viewModelFactory).get(SelectedRestaurantsViewModel.class);
        this.cache = ((Launch)this.getActivity().getApplication()).cache();
        // UI
        View root = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        Context context = root.getContext();
        this.recyclerView = (RecyclerView) root;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        this.adapter = new ListRestaurantRecyclerViewAdapter();

        this.observeSelectedRestaurantsLiveData();
        this.updateRestaurantsDataFromMyPosition();
        return root;
    }

    private void observeSelectedRestaurantsLiveData() {
        this.selectedRestaurantsViewModel.getRestaurants().observe(this.getViewLifecycleOwner(), restaurants -> {
            this.adapter.updateList(restaurants);
            this.recyclerView.setAdapter(adapter);
        });
    }

    private void updateRestaurantsDataFromMyPosition() {
        this.cache.getMyPosition().observe(this.getViewLifecycleOwner(), geolocation -> {
            if(geolocation != null) {
                try {

                    this.selectedRestaurantsViewModel.fetchRestaurantsToUpdateLiveData(
                            geolocation.getLatitude(),
                            geolocation.getLongitude(),
                            1000
                    );

                } catch(Exception e) {
                    Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
