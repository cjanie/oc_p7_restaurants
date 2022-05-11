package com.android.go4lunch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.R;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;

public class WebViewFragment extends Fragment {

    private RestaurantDetailsViewModel restaurantDetailsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_view, container, false);

        this.restaurantDetailsViewModel = new ViewModelProvider(this.getActivity()).get(RestaurantDetailsViewModel.class);
        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurant();
        if(restaurant != null || restaurant.getWebSite() != null) {
            ((WebView) root).loadUrl(restaurant.getWebSite());
        }

        return root;
    }
}
