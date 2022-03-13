package com.android.go4lunch.ui.listeners;

import com.android.go4lunch.ui.events.LikeEvent;

import org.greenrobot.eventbus.EventBus;

public class LikeClickListener implements ButtonClickListener {
    @Override
    public void action() {
        EventBus.getDefault().post(new LikeEvent());
    }
}
