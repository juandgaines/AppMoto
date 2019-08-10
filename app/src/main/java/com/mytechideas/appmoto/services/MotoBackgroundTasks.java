package com.mytechideas.appmoto.services;

import android.content.Context;

import com.mytechideas.appmoto.preferences.PrefMang;

public class MotoBackgroundTasks{

    public static final String ACTION_FOLLOW_GPS="gps-sendind-info-to-server";

    public static void  executeTasks(Context context, String action){

        if (ACTION_FOLLOW_GPS.equals(action)){
            //do
            PrefMang.setTest();
        }
    }

}
