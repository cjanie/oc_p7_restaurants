package com.android.go4lunch.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.valueobjects.WorkmateValueObject;
import com.android.go4lunch.ui.RestaurantDetailsActivity;
import com.android.go4lunch.ui.intentConfigs.RestaurantDetailsActivityIntentConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ListWorkmateRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY_VIEW = 0;

    private static final int TYPE_ITEM_VIEW = 1;

    private List<WorkmateValueObject> workmates;

    public ListWorkmateRecyclerViewAdapter(List<WorkmateValueObject> workmates) {
        this.workmates = workmates;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_ITEM_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workmate_list_item, parent, false);
            return new WorkmateViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workmate_list_empty, parent, false);
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof WorkmateViewHolder) {
            WorkmateValueObject workmate = this.workmates.get(position);
            // Avatar
            Glide.with(((WorkmateViewHolder) holder).avatar.getContext())
                    .load(workmate.getWorkmate().getUrlPhoto())
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(((WorkmateViewHolder) holder).avatar);
            // Text
            StringBuilder stringBuilder = new StringBuilder(workmate.getWorkmate().getName());
            if(workmate.getSelection() == null) {
                stringBuilder.append(" " + holder.itemView.getContext().getString(R.string.has_not_decided));
            } else {
                stringBuilder.append(" " + holder.itemView.getContext().getString(R.string.is_eating)+ " (" + workmate.getSelection().getName() + ")");
            }
            ((WorkmateViewHolder) holder).text.setText(stringBuilder.toString());

            if(workmate.getSelection() != null) {
                holder.itemView.setOnClickListener(view -> {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_ID, workmate.getSelection().getId());
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_NAME, workmate.getSelection().getName());
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_ADDRESS, workmate.getSelection().getAddress());
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_PHONE, workmate.getSelection().getPhone());
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_WEB_SITE, workmate.getSelection().getWebSite());
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_PHOTO_URL, workmate.getSelection().getPhotoUrl());
                        context.startActivity(intent);
                    }
                );
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
            if(this.workmates.isEmpty() && position == 0) {
                return TYPE_EMPTY_VIEW;
            }
        return TYPE_ITEM_VIEW;
    }

    @Override
    public int getItemCount() {
        if(this.workmates.isEmpty()) {
            return 1;
        }
        return this.workmates.size();
    }

}

