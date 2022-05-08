package com.android.go4lunch.ui.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VisitorViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.visitor_avatar)
    ImageView avatar;
    @BindView(R.id.visitor_name)
    TextView name;

    public VisitorViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}