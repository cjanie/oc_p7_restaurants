package com.android.go4lunch.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;

import com.android.go4lunch.ui.adapters.ListRestaurantRecyclerViewAdapter;
import com.android.go4lunch.ui.events.InitMyPositionEvent;
import com.android.go4lunch.ui.viewmodels.RestaurantsViewModel;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class ListRestaurantFragment extends WithLocationPermissionFragment {

    private RestaurantsViewModel restaurantsViewModel;

    RecyclerView recyclerView;

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

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void setListWhenInitMyPosition(InitMyPositionEvent event) {
        this.restaurantsViewModel.getRestaurants(event.getLatitude(), event.getLongitude(), 1000)
                .observe(this, restaurants -> {
                    if(!restaurants.isEmpty()) {
                        ListRestaurantRecyclerViewAdapter adapter = new ListRestaurantRecyclerViewAdapter(restaurants);
                        this.recyclerView.setAdapter(adapter);
                    }
        });
    }
}
