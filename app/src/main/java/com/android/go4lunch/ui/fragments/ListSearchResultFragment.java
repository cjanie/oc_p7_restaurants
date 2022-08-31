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
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.ui.Cache;
import com.android.go4lunch.ui.adapters.ListRestaurantRecyclerViewAdapter;
import com.android.go4lunch.ui.viewmodels.RestaurantsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListSearchResultFragment extends Fragment {

    private RestaurantsViewModel restaurantsViewModel;

    private Cache cache;

    private RecyclerView recyclerView;

    private ListRestaurantRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.restaurantsViewModel = new ViewModelProvider(
                this,
                ((Launch) this.getActivity().getApplication()).restaurantsViewModelFactory()
        ).get(RestaurantsViewModel.class);

        this.cache = ((Launch) this.getActivity().getApplication()).cache();

        View root = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        Context context = root.getContext();
        this.recyclerView = (RecyclerView) root;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        this.adapter = new ListRestaurantRecyclerViewAdapter();

        this.observeSearchResult();

        this.cache.getRestaurantIdForSearch().observe(this.getViewLifecycleOwner(), id -> {
            if(id != null || !id.isEmpty()) {
                this.updateSearchResult(id);
            }
        });


        return root;
    }

    private void observeSearchResult() {
        this.restaurantsViewModel.getSearchResult().observe(this.getViewLifecycleOwner(),
                restaurant -> {
                    if(restaurant != null) {
                        List<RestaurantValueObject> restaurants = new ArrayList<>();
                        restaurants.add(restaurant);
                        this.adapter.updateList(restaurants);
                        this.recyclerView.setAdapter(adapter);
                    }
                });
    }

    private void updateSearchResult(String restaurantId) {
        this.cache.getMyPosition().observe(this.getViewLifecycleOwner(), geolocation -> {
            if(geolocation != null) {
                try {
                    this.restaurantsViewModel.fetchSearchResultToUpdateLiveData(restaurantId, geolocation.getLatitude(), geolocation.getLongitude());
                } catch(Exception e) {
                    Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
/*
    @Override
    public void onResume() {
        super.onResume();
        this.cache.getRestaurantIdForSearch().observe(this.getViewLifecycleOwner(), id -> {
            if(id != null || !id.isEmpty()) {
                this.updateSearchResult(id);
            }
        });
    }

 */
}
