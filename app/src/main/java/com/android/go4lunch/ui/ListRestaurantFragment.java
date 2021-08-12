package com.android.go4lunch.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;

import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.DistanceInfo;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.android.go4lunch.ui.adapters.ListRestaurantRecyclerViewAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.List;

public class ListRestaurantFragment extends WithLocationPermissionFragment {

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
            this.initLocation(restaurants);
        });
    }

    @Override
    protected void initLocation(List<RestaurantVO> restaurants) {
        this.fusedlocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        if(ActivityCompat.checkSelfPermission(
                this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            this.fusedlocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this.getActivity(), location -> {
                        if(location != null) {
                            Double x = location.getLatitude();
                            Double y = location.getLongitude();
                            Geolocation myPosition = new Geolocation(x, y);

                            for(RestaurantVO r: restaurants) {
                                r.setDistanceInfo(new DistanceInfo().getSquareDistanceRoundDown(myPosition, r.getRestaurant().getGeolocation()));
                                //r.setDistanceInfo(new DistanceInfo().getSquareDistanceRoundDown(new Geolocation(37.421994, -122.0840022), r.getRestaurant().getGeolocation()));
                            }

                            // set adapter with list to recycler view
                            ListRestaurantRecyclerViewAdapter adapter = new ListRestaurantRecyclerViewAdapter(restaurants);
                            this.recyclerView.setAdapter(adapter);

                            System.out.println("latitude: " + myPosition.getX() + "xxxxxxxxxxxxxxxxxxxx");
                            System.out.println("longitude: " + myPosition.getY() + "xxxxxxxxxxxxxxxxxxxx");

                            return;
                        } else {
                            System.out.println("location is null");
                        }
                    })
                    .addOnFailureListener(this.getActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("getlastlocation failed" + "************" + e.getMessage());

                        }
                    });
        } else {
            this.showEducationalUI();
        }
    }



    private void loadDistanceInfo(Location location) {

    }

}
