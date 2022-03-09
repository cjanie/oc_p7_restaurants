package com.android.go4lunch.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.usecases.models_vo.WorkmateVO;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListWorkmateRecyclerViewAdapter extends RecyclerView.Adapter<ListWorkmateRecyclerViewAdapter.ViewHolder> {

    private List<WorkmateVO> workmates;

    public ListWorkmateRecyclerViewAdapter(List<WorkmateVO> workmates) {
        this.workmates = workmates;
    }

    @NonNull
    @Override
    public ListWorkmateRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workmate_list_item, parent, false);
        return new ListWorkmateRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListWorkmateRecyclerViewAdapter.ViewHolder holder, int position) {
        WorkmateVO workmate = this.workmates.get(position);
        // Avatar
        Glide.with(holder.avatar.getContext())
                .load(workmate.getWorkmate().getUrlPhoto())
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(holder.avatar);
        // Text
        StringBuilder stringBuilder = new StringBuilder(workmate.getWorkmate().getName());
        if(workmate.getSelection() == null) {
            stringBuilder.append(" " + holder.itemView.getContext().getString(R.string.has_not_decided));
        } else {
            stringBuilder.append(" " + holder.itemView.getContext().getString(R.string.is_eating)+ " (" + workmate.getSelection().getName() + ")");
        }
        holder.text.setText(stringBuilder.toString());

    }

    @Override
    public int getItemCount() {
        return this.workmates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.workmate_avatar)
        ImageView avatar;
        @BindView(R.id.workmate_text)
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
