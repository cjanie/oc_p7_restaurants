package com.android.go4lunch.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.models.Workmate;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ListVisitorRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY_VIEW = 0;

    private static final int TYPE_ITEM_VIEW = 1;

    private List<Workmate> workmates;

    public ListVisitorRecyclerViewAdapter(List<Workmate> workmates) {
        this.workmates = workmates;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_ITEM_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_visitor_list_item, parent, false);
            return new VisitorViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workmate_list_empty, parent, false);
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VisitorViewHolder) {
            Workmate workmate = this.workmates.get(position);
            if(workmate.getUrlPhoto() != null) {
                Glide.with(((VisitorViewHolder) holder).avatar.getContext())
                        .load(workmate.getUrlPhoto())
                        .apply(RequestOptions.circleCropTransform())
                        .error(R.drawable.ic_baseline_error_24)
                        .into(((VisitorViewHolder) holder).avatar);
            } else {
                ((VisitorViewHolder) holder).avatar.setImageResource(R.drawable.ic_baseline_person_24);
            }
            ((VisitorViewHolder) holder).name.setText(workmate.getName());
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
