package com.android.go4lunch.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.ui.RestaurantDetailsActivity;
import com.android.go4lunch.usecases.models.RestaurantModel;
import com.android.go4lunch.ui.utils.TimeInfoTextHandler;
import com.android.go4lunch.usecases.enums.Vote;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListRestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY_VIEW = 0;

    private static final int TYPE_ITEM_VIEW = 1;

    private final List<RestaurantModel> restaurantModels;

    public ListRestaurantRecyclerViewAdapter(List<RestaurantModel> restaurantModels) {
        this.restaurantModels = restaurantModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_ITEM_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_restaurant_list_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_restaurant_list_empty, parent, false);
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            RestaurantModel restaurant = this.restaurantModels.get(position);
            Glide.with(((ItemViewHolder) holder).photo.getContext())
                    .load(restaurant.getRestaurant().getPhotoUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(((ItemViewHolder) holder).photo);
            ((ItemViewHolder) holder).name.setText(restaurant.getRestaurant().getName());
            ((ItemViewHolder)holder).address.setText(restaurant.getRestaurant().getAddress());

            if(restaurant.getTimeInfo() != null) {
                TimeInfoTextHandler timeInfoTextHandler = new TimeInfoTextHandler();
                ((ItemViewHolder)holder).info.setText(timeInfoTextHandler.getText(restaurant, ((ItemViewHolder) holder).info.getContext()));
                ((ItemViewHolder)holder).info.setTextColor(timeInfoTextHandler.getColor(restaurant, ((ItemViewHolder)holder).info));
                ((ItemViewHolder)holder).info.setTypeface(null, timeInfoTextHandler.getStyle(restaurant));
            }

            if(restaurant.getDistance() != null) {
                ((ItemViewHolder)holder).distance.setText(
                        restaurant.getDistance().toString()
                                + ((ItemViewHolder) holder).distance.getContext().getString(R.string.meter_abbrev));
            }

            ((ItemViewHolder)holder).selections.setText("(" + restaurant.getVisitorsCount() +")");

            /*
            Vote vote = restaurant.getVoteInfo();

            if(vote == Vote.ONE_STAR) {
                ((ItemViewHolder)holder).starsContainer.addView(this.createStar(holder.itemView.getContext()));
            }
            if(vote == Vote.TWO_STARS) {
                for(int i=0; i<2; i++) {
                    ((ItemViewHolder)holder).starsContainer.addView(this.createStar(holder.itemView.getContext()));
                }
            }
            if(vote == Vote.THREE_STARS) {
                for(int i=0; i<3; i++) {
                    ((ItemViewHolder)holder).starsContainer.addView(this.createStar(holder.itemView.getContext()));
                }
            }

             */
            ((ItemViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                    intent.putExtra("id", restaurant.getRestaurant().getId());
                    intent.putExtra("name", restaurant.getRestaurant().getName());
                    intent.putExtra("address", restaurant.getRestaurant().getAddress());
                    intent.putExtra("photoUrl", restaurant.getRestaurant().getPhotoUrl());
                    intent.putExtra("phone", restaurant.getRestaurant().getPhone());
                    intent.putExtra("website", restaurant.getRestaurant().getWebSite());
                    context.startActivity(intent);
                }
            });
        }

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
    public int getItemViewType(int position) {
        if(this.restaurantModels.isEmpty() && position == 0) {
            return TYPE_EMPTY_VIEW;
        } else {
            return TYPE_ITEM_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        if(this.restaurantModels.isEmpty()) {
            return 1;
        }
        return this.restaurantModels.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
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

        @BindView(R.id.photo)
        ImageView photo;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
