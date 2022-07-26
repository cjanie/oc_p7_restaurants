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
import com.android.go4lunch.ui.Mode;
import com.android.go4lunch.ui.loader.LoadingDialog;
import com.android.go4lunch.ui.adapters.ListRestaurantRecyclerViewAdapter;
import com.android.go4lunch.ui.viewmodels.RestaurantsViewModel;
import com.android.go4lunch.ui.viewmodels.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListRestaurantFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    private RestaurantsViewModel restaurantsViewModel;

    private Cache cache;

    private RecyclerView recyclerView;

    private ListRestaurantRecyclerViewAdapter adapter;

    private LoadingDialog loadingDialog;

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

        this.cache = ((Launch) this.getActivity().getApplication()).cache();

        // UI
        View root = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        Context context = root.getContext();
        this.recyclerView = (RecyclerView) root;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        this.adapter = new ListRestaurantRecyclerViewAdapter();
        this.loadingDialog = new LoadingDialog(this.getActivity());

        // Listening to the results of the view model actions
        this.observeIsLoading();

        this.cache.getMode().observe(this.getViewLifecycleOwner(), mode -> {
            if(mode.equals(Mode.LIST)) {
                this.observeRestaurantsData();
            }
            if(mode.equals(Mode.SEARCH)) {
                this.observeSearchResult();
            }
        });

        // Call the View model actions
        this.updateRestaurantsDataFromMyPosition();

        this.cache.getRestaurantIdForSearch().observe(this.getViewLifecycleOwner(), id -> {
            if(id != null || !id.isEmpty()) {
                this.updateSearchResult(id);
            }
        });

        return root;
    }

    private void observeRestaurantsData() {
        this.restaurantsViewModel.getRestaurants().observe(this.getViewLifecycleOwner(),
                restaurants -> {
                    this.adapter.updateList(restaurants);
                    this.recyclerView.setAdapter(adapter);
                });
        this.restaurantsViewModel.getSearchResult().removeObservers(this.getViewLifecycleOwner());
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
        this.restaurantsViewModel.getRestaurants().removeObservers(this.getViewLifecycleOwner());
    }

    private void updateRestaurantsDataFromMyPosition() {
        this.sharedViewModel.getGeolocation().observe(this.getViewLifecycleOwner(), geolocation -> {
            if(geolocation != null) {
                try {

                    this.restaurantsViewModel.fetchRestaurantsObservableToUpdateLiveData(
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

    private void updateSearchResult(String restaurantId) {
        this.sharedViewModel.getGeolocation().observe(this.getViewLifecycleOwner(), geolocation -> {
            if(geolocation != null) {
                try {
                    this.restaurantsViewModel.fetchSearchResultToUpdateLiveData(restaurantId, geolocation.getLatitude(), geolocation.getLongitude());
                } catch(Exception e) {
                    Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
// TODO Loading
    private void observeIsLoading() {
        this.restaurantsViewModel.isLoading().observe(this.getViewLifecycleOwner(), isLoading -> {
            if(isLoading) this.loadingDialog.showLoadingDialog();
            else this.loadingDialog.dismissDialog();
        });
    }

}
