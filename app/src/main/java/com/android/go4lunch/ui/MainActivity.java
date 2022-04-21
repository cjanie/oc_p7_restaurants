package com.android.go4lunch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.android.go4lunch.R;

import com.google.android.material.navigation.NavigationView;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    // UI
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    NavController navController;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;



    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // UI
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.setSupportActionBar(toolbar);

        /*
        this.appBarConfiguration = new AppBarConfiguration.Builder(
                Stream.of(R.id.your_lunch, R.id.settings, R.id.logout).collect(Collectors.toSet())
        )
                .setOpenableLayout(this.drawerLayout)
                .build();
        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, this.navController, this.appBarConfiguration);
        //NavigationUI.setupWithNavController(this.navigationView, this.navController);
        */
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });
        this.navigationView.setCheckedItem(R.id.your_lunch);
        this.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.settings) {

                }
                if(item.getItemId() == R.id.logout) {

                }
                drawerLayout.close();
                return false;
            }
        });

    }



    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
        //return this.navController.navigateUp();
    }
}