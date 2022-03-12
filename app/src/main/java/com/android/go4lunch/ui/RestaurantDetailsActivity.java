package com.android.go4lunch.ui;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.android.go4lunch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailsActivity extends BaseActivity {

    @BindView(R.id.buttons_bar)
    LinearLayout buttonsBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);


    }
}
