package com.android.go4lunch.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.enums.Vote;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;
import com.android.go4lunch.write.businesslogic.usecases.events.ToggleSelectionEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListRestaurantRecyclerViewAdapter extends RecyclerView.Adapter<ListRestaurantRecyclerViewAdapter.ViewHolder> {

    private final List<RestaurantVO> restaurantVOs;

    public ListRestaurantRecyclerViewAdapter(List<RestaurantVO> restaurantVOs) {

        this.restaurantVOs = restaurantVOs;
    }

    @NonNull
    @Override
    public ListRestaurantRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_restaurant_list_item, parent, false);
        return new ListRestaurantRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRestaurantRecyclerViewAdapter.ViewHolder holder, int position) {
        RestaurantVO restaurant = this.restaurantVOs.get(position);
        holder.name.setText(restaurant.getRestaurant().getName());
        holder.address.setText(restaurant.getRestaurant().getAddress());
        holder.info.setText(restaurant.getTimeInfo().toString());
        if(restaurant.getDistanceInfo() != null) {
            holder.distance.setText(restaurant.getDistanceInfo().toString());
        }
        holder.selections.setText("(" + String.valueOf(restaurant.getSelectionCountInfo()) +")");

        // Vote
        ConstraintLayout constraintLayout = new ConstraintLayout(holder.itemView.getContext());

        ImageView star = new ImageView(holder.itemView.getContext());
        star.setImageDrawable(star.getContext().getDrawable(R.drawable.ic_baseline_star_24));
        ImageView starB = new ImageView(holder.itemView.getContext());
        starB.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_baseline_star_border_24));
        constraintLayout.addView(star);
        constraintLayout.addView(starB);

        ConstraintLayout constraintLayout2 = new ConstraintLayout(holder.itemView.getContext());
        ImageView star2 = new ImageView(holder.itemView.getContext());
        star2.setImageDrawable(star.getContext().getDrawable(R.drawable.ic_baseline_star_24));
        ImageView star2B = new ImageView(holder.itemView.getContext());
        star2B.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_baseline_star_border_24));
        constraintLayout2.addView(star2);
        constraintLayout2.addView(star2B);

        ConstraintLayout constraintLayout3 = new ConstraintLayout(holder.itemView.getContext());
        ImageView star3 = new ImageView(holder.itemView.getContext());
        star3.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_baseline_star_24));
        ImageView star3B = new ImageView(holder.itemView.getContext());
        star3B.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_baseline_star_border_24));
        constraintLayout3.addView(star3);
        constraintLayout3.addView(star3B);


        Vote vote = restaurant.getVoteInfo();

        if(vote == Vote.ONE_STAR) {
            holder.starsContainer.addView(constraintLayout);
        }
        if(vote == Vote.TWO_STARS) {
            holder.starsContainer.addView(constraintLayout);

            holder.starsContainer.addView(constraintLayout2);
        }
        if(vote == Vote.THREE_STARS) {
            holder.starsContainer.addView(constraintLayout);
            holder.starsContainer.addView(constraintLayout2);
            holder.starsContainer.addView(constraintLayout3);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ToggleSelectionEvent(restaurant.getRestaurant()));
            }
        });
    }



    @Override
    public int getItemCount() {
        return this.restaurantVOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_name)
        TextView name;

        @BindView(R.id.address)
        TextView address;

        @BindView(R.id.restaurant_info)
        TextView info;

        @BindView(R.id.distance)
        TextView distance;

        @BindView(R.id.selections)
        TextView selections;

        @BindView(R.id.starts_container)
        LinearLayout starsContainer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
