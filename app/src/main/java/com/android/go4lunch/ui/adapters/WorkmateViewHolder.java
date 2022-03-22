package com.android.go4lunch.ui.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkmateViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.workmate_avatar)
    ImageView avatar;
    @BindView(R.id.workmate_text)
    TextView text;

    public WorkmateViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
