package com.android.go4lunch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.go4lunch.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    // UI
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // UI
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.setSupportActionBar(toolbar);

        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        Glide.with(
                (ImageView) this.navigationView.getHeaderView(0).findViewById(R.id.photo_session)
        )
                .load("https://i.pravatar.cc/150?u=a042581f4e29026704d")
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.ic_baseline_error_24)
                .into(
                        (ImageView) this.navigationView.getHeaderView(0).findViewById(R.id.photo_session)
                );
        ((TextView) this.navigationView.getHeaderView(0).findViewById(R.id.name_session)).setText("Jojo");
        ((TextView) this.navigationView.getHeaderView(0).findViewById(R.id.email_session)).setText("Jojo@com");

        this.navigationView.setCheckedItem(R.id.your_lunch);
        this.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.settings) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.logout) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.close();
                return false; // corresponds to is checked
            }
        });
    }

    @Override
    public void onBackPressed() {
        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.on_back_pressed_disabled, Snackbar.LENGTH_LONG).show();
    }
}