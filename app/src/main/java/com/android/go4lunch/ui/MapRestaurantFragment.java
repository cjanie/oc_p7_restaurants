package com.android.go4lunch.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.R;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapRestaurantFragment extends WithLocationPermissionFragment {

    private MapViewModel mapViewModel;

    @BindView(R.id.web_view)
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.mapViewModel = new ViewModelProvider(this.getActivity()).get(MapViewModel.class);

        View root = inflater.inflate(R.layout.fragment_restaurant_map, container, false);
        ButterKnife.bind(this, root);
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.loadUrl("http://google.com");
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mapViewModel.list().observe(this.getActivity(), restaurants -> {
            //this.initLocation(restaurants);
        });
    }

    @Override
    protected void initLocation(List<RestaurantVO> restaurants) {
        // TODO WITH RIGHT US MAP VIEW MODEL
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
                                // TODO
                            }

                            // set UI TODO

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
}
