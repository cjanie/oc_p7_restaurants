package com.android.go4lunch.ui;

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
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.model.CustomLocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.ui.adapters.ListRestaurantRecyclerViewAdapter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class ListRestaurantFragment extends Fragment {

    private RestaurantViewModel restaurantViewModel;





    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        this.restaurantViewModel = new ViewModelProvider(ListRestaurantFragment.this).get(RestaurantViewModel.class);

        this.recyclerView = (RecyclerView) root;
        Context context = root.getContext();
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.restaurantViewModel.list().observe(this, restaurants -> {
            ListRestaurantRecyclerViewAdapter adapter = new ListRestaurantRecyclerViewAdapter(restaurants);
            this.recyclerView.setAdapter(adapter);
            System.out.println(restaurants.size() + "restaurants ********");
        });
    }
}
