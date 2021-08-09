package com.android.go4lunch.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.go4lunch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapRestaurantFragment extends Fragment {

    @BindView(R.id.web_view)
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurant_map, container, false);
        ButterKnife.bind(this, root);
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.loadUrl("http://google.com");
        return root;
    }


}
