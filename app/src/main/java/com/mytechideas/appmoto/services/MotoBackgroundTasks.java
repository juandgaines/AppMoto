package com.mytechideas.appmoto.services;

import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mytechideas.appmoto.context.AppMotoContext;
import com.mytechideas.appmoto.preferences.PrefMang;
import com.mytechideas.appmoto.utils.NotificationUtils;

public class MotoBackgroundTasks{
    public static final String LOG_TAG=MotoBackgroundTasks.class.getSimpleName();

    public static final String ACTION_SEND_SENSORS="send-sensors-to-server";
    public static final String ACTION_STOP_SENSORS="stop-sensors-to-server";

    public static void  executeTasks(Context context, String action){

        if (ACTION_SEND_SENSORS.equals(action)){
            Log.d(LOG_TAG, "Enviando sensores....");
            NotificationUtils.createNotificationMotoCurrentlySharing(context);
        }

        else if (ACTION_STOP_SENSORS.equals(action)){
            Log.d(LOG_TAG, "Detener sensores....");
            NotificationUtils.closeNotification(context);
        }
    }

}
