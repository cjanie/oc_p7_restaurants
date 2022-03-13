package com.android.go4lunch.ui.listeners;

import com.android.go4lunch.ui.events.WebsiteEvent;
import com.android.go4lunch.ui.listeners.ButtonClickListener;

import org.greenrobot.eventbus.EventBus;

public class WebsiteClickListener implements ButtonClickListener {
    @Override
    public void action() {
        EventBus.getDefault().post(new WebsiteEvent());
    }
}
