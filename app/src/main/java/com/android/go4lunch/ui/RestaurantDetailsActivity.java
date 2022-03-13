package com.android.go4lunch.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.ui.adapters.ListVisitorRecyclerViewAdapter;
import com.android.go4lunch.ui.adapters.ListWorkmateRecyclerViewAdapter;
import com.android.go4lunch.ui.events.CallEvent;
import com.android.go4lunch.ui.events.LikeEvent;
import com.android.go4lunch.ui.events.WebsiteEvent;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;
import com.android.go4lunch.usecases.models_vo.WorkmateVO;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailsActivity extends BaseActivity {

    @BindView(R.id.details_restaurant_pastille)
    FrameLayout pastille;

    @BindView(R.id.details_restaurant_image)
    ImageView restaurantImage;

    @BindView(R.id.details_restaurant_name)
    TextView restaurantName;

    @BindView(R.id.details_restaurant_address)
    TextView restaurantAddress;

    @BindView(R.id.workmates_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);

        // Data
        Restaurant restaurant = new Restaurant("Chez Jojo", "12 allée des lilas");
        restaurant.setId("1");
        if(restaurant.getPhotoUrl() != null) {
            Glide.with(this.restaurantImage.getContext())
                    .load(restaurant.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.ic_baseline_error_24)
                    .into(this.restaurantImage);
        }
        this.restaurantName.setText(restaurant.getName());
        this.restaurantAddress.setText(restaurant.getAddress());
        Workmate janie = new Workmate("Janie");
        List<Workmate> workmates = new ArrayList<>();
        //workmates.add(janie);
        ListVisitorRecyclerViewAdapter adapter = new ListVisitorRecyclerViewAdapter(workmates);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if(workmates.isEmpty()) {
            pastille.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void whenClickLike(LikeEvent event) {
        Log.i("Details Activity", "event click LIKE receipt");
        this.handleClickLike();
    }

    @Subscribe
    public void whenClickCall(CallEvent event) {
        Log.i("Details Activity", "event click CALL receipt");
    }

    @Subscribe
    public void whenClickWebsite(WebsiteEvent event) {
        Log.i("Details Activity", "event click WEBSITE receipt");
    }

    private void handleClickLike() {
        this.addSelection();
    }

    private void addSelection() {

    }
}
