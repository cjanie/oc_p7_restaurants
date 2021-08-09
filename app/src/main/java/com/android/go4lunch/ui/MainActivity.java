package com.android.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.android.go4lunch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private RestaurantViewModel restaurantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.restaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.restaurantViewModel.list().observe(this, restaurants -> {

            System.out.println(restaurants.size() + "restaurants ********");
        });

    }
}