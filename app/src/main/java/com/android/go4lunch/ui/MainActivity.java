package com.android.go4lunch.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;

import com.android.go4lunch.R;
import com.android.go4lunch.read.adapter.AndroidLocationProvider;
import com.android.go4lunch.read.businesslogic.usecases.model.DistanceInfo;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {



    private LocationViewModel locationViewModel;


    @BindView(R.id.share_location_button)
    public Button shareLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
    }

    @OnClick(R.id.share_location_button)
    public void onShareLocation() {
        DistanceInfo distanceInfo = new DistanceInfo(new AndroidLocationProvider(this));
        this.locationViewModel.setDistanceInfoValue(distanceInfo);
    }
}