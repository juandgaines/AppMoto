package com.mytechideas.appmoto.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.mytechideas.appmoto.activities.dashboardactivity.DashBoardActivity;

import static com.mytechideas.appmoto.services.MotoBackgroundTasks.ACTION_SEND_SENSORS;
import static com.mytechideas.appmoto.services.MotoBackgroundTasks.ACTION_STOP_SENSORS;

public class MotoBackgroundService extends IntentService {

    private final IBinder binder = new LocalBinder();

    private Boolean serviceState =false;

    public class LocalBinder extends Binder{

        public MotoBackgroundService getService(){
            return MotoBackgroundService.this;
        }
    }


    public Boolean getServiceState(){
        return serviceState;
    }

    public void setServiceState(Boolean serviceState) {
        this.serviceState = serviceState;
    }

    public void stopService(){
        MotoBackgroundTasks.executeTasks(this,ACTION_STOP_SENSORS);
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stopSelf();
        return true;
    }

    public void forceAction(Intent intent){
        onHandleIntent(intent);
    }

    public MotoBackgroundService() {
        super(MotoBackgroundService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String action= intent.getAction();

        MotoBackgroundTasks.executeTasks(this,action);

        if (action.equals(ACTION_STOP_SENSORS)) {
            serviceState=false;
        }
        else if(action.equals(ACTION_SEND_SENSORS)){
            serviceState=true;
        }

    }


}
