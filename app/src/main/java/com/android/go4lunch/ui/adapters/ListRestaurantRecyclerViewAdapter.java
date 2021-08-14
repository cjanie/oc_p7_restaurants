package com.android.go4lunch.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
