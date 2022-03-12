package com.android.go4lunch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.go4lunch.R;
import com.android.go4lunch.ui.listeners.ButtonClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButtonFragment extends Fragment {

    private int drawableId;

    private int stringId;

    private ButtonClickListener buttonClickListener;

    public ButtonFragment(int drawableId, int stringId, ButtonClickListener buttonClickListener) {
        this.drawableId = drawableId;
        this.stringId = stringId;
        this.buttonClickListener = buttonClickListener;
    }

    @BindView(R.id.button_icon)
    ImageView icon;

    @BindView(R.id.button_text)
    TextView text;

    @OnClick(R.id.button_container)
    public void onClick() {
        this.buttonClickListener.action();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_button, container, false);
        ButterKnife.bind(this, root);

        icon.setBackgroundResource(this.drawableId);
        text.setText(this.stringId);

        return root;
    }
}
