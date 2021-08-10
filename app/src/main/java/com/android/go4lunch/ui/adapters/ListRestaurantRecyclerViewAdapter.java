package com.android.go4lunch.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.DistanceUnity;
import com.android.go4lunch.read.businesslogic.usecases.model.DistanceUnityController;

import java.util.List;
import java.util.Map;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item_fragment, parent, false);
        return new ListRestaurantRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRestaurantRecyclerViewAdapter.ViewHolder holder, int position) {
        RestaurantVO restaurant = this.restaurantVOs.get(position);
        holder.name.setText(restaurant.getName());
        holder.address.setText(restaurant.getAddress());
        holder.info.setText(restaurant.getInfo().toString());
        if(restaurant.getDistanceInfo() != null) {
            DistanceUnity unity = DistanceUnity.UNITY; // TODO METER
            DistanceUnityController distanceUnityController = new DistanceUnityController(unity);
            Map<DistanceUnity, Long> map = distanceUnityController.getDistanceInfo(restaurant.getDistanceInfo());
            Long l = map.get(unity);
            holder.distance.setText(l + unity.toString());
        }
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
