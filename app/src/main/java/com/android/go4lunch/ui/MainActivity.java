package com.android.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;


import com.android.go4lunch.R;


import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.repositories.SessionRepository;
import com.android.go4lunch.ui.adapters.ViewPagerAdapter;

import com.facebook.AccessToken;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    // UI
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.container)
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // UI
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // ViewPager shows :
        // map
        // or list of restaurants
        // or workmates,
        // Controlled by ViewPagerAdapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager(), this.getLifecycle());
        this.viewPager.setAdapter(viewPagerAdapter);

        // attach tabLayout to viewPager
        String[] tabsTexts = new String[] {
                this.getResources().getString(R.string.map_view),
                this.getResources().getString(R.string.list_view),
                this.getResources().getString(R.string.workmates)
        };
        Drawable[] tabsIcons = new Drawable[] {
                this.getResources().getDrawable(R.drawable.ic_baseline_map_24),
                this.getResources().getDrawable(R.drawable.ic_baseline_view_list_24),
                this.getResources().getDrawable(R.drawable.ic_baseline_group_24)
        };

        new TabLayoutMediator(this.tabLayout, this.viewPager,
                (tab, position) -> {
           tab.setText(tabsTexts[position]);
           tab.setIcon(tabsIcons[position]);
        }).attach();
        
    }

}