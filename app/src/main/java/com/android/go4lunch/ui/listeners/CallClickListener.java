package com.android.go4lunch.ui.listeners;

import com.android.go4lunch.ui.events.CallEvent;

import org.greenrobot.eventbus.EventBus;

public class CallClickListener implements ButtonClickListener {
    @Override
    public void action() {
        EventBus.getDefault().post(new CallEvent());
    }
}
