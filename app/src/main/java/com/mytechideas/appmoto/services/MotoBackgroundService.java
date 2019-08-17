package com.mytechideas.appmoto.services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import static com.mytechideas.appmoto.services.MotoBackgroundTasks.ACTION_STOP_SENSORS;

public class MotoBackgroundService extends IntentService {

    public MotoBackgroundService() {
        super(MotoBackgroundService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String action= intent.getAction();
        MotoBackgroundTasks.executeTasks(this,action);
        if (action.equals(ACTION_STOP_SENSORS))
            stopSelf();

    }


}
