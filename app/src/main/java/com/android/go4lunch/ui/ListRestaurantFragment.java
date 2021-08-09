package com.android.go4lunch.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.ui.adapters.ListRestaurantRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListRestaurantFragment extends Fragment {

    private List<RestaurantVO> restaurants;

    public ListRestaurantFragment(List<RestaurantVO> restaurants) {
        this.restaurants = restaurants;
    }

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        this.recyclerView = (RecyclerView) root;
        Context context = root.getContext();
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        ListRestaurantRecyclerViewAdapter adapter = new ListRestaurantRecyclerViewAdapter(this.restaurants);
        this.recyclerView.setAdapter(adapter);
        return root;
    }


}
