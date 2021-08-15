package com.android.go4lunch.ui.adapters;

import android.content.Context;
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
        Vote vote = restaurant.getVoteInfo();

        if(vote == Vote.ONE_STAR) {
            holder.starsContainer.addView(this.createStar(holder.itemView.getContext()));
        }
        if(vote == Vote.TWO_STARS) {
            for(int i=0; i<2; i++) {
                holder.starsContainer.addView(this.createStar(holder.itemView.getContext()));
            }
        }
        if(vote == Vote.THREE_STARS) {
            for(int i=0; i<3; i++) {
                holder.starsContainer.addView(this.createStar(holder.itemView.getContext()));
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ToggleSelectionEvent(restaurant.getRestaurant()));
            }
        });
    }

    private ConstraintLayout createStar(Context context) {
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        ImageView star = new ImageView(context);
        star.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_star_24));
        ImageView starBorder = new ImageView(context);
        starBorder.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_star_border_24));

        constraintLayout.addView(star);
        constraintLayout.addView(starBorder);

        return constraintLayout;
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
