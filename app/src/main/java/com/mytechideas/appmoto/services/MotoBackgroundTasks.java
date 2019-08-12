package com.mytechideas.appmoto.services;

import android.app.Notification;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;
import android.widget.Toast;

import com.github.pwittchen.reactivesensors.library.ReactiveSensorEvent;
import com.github.pwittchen.reactivesensors.library.ReactiveSensorFilter;
import com.github.pwittchen.reactivesensors.library.ReactiveSensors;
import com.mytechideas.appmoto.context.AppMotoContext;
import com.mytechideas.appmoto.preferences.PrefMang;
import com.mytechideas.appmoto.utils.NotificationUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MotoBackgroundTasks {
    public static final String LOG_TAG=MotoBackgroundTasks.class.getSimpleName();

    public static final String ACTION_SEND_SENSORS="send-sensors-to-server";
    public static final String ACTION_STOP_SENSORS="stop-sensors-to-server";

    private static CompositeDisposable observablesSensors=new CompositeDisposable();

    public static void  executeTasks(Context context, String action){

        if (ACTION_SEND_SENSORS.equals(action)){
            Log.d(LOG_TAG, "Enviando sensores....");
            NotificationUtils.createNotificationMotoCurrentlySharing(context);
            observablesSensors.add(createObservableForSensorGyroscope(context));
            observablesSensors.add(createObservableForSensorAccelerometer(context));
        }

        else if (ACTION_STOP_SENSORS.equals(action)){
            Log.d(LOG_TAG, "Detener sensores....");
            observablesSensors.dispose();
            NotificationUtils.closeNotification(context);
        }
    }

    public static Disposable createObservableForSensorGyroscope(Context context){

        Disposable observable = new ReactiveSensors(context).observeSensor(Sensor.TYPE_GYROSCOPE)
                .subscribeOn(Schedulers.computation())
                .filter(ReactiveSensorFilter.filterSensorChanged())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReactiveSensorEvent>() {

                    @Override
                    public void accept(ReactiveSensorEvent reactiveSensorEvent) throws Exception {
                        SensorEvent event = reactiveSensorEvent.getSensorEvent();

                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];

                        String message = String.format("Gyroscope: x = %f, y = %f, z = %f", x, y, z);
                        Log.d(LOG_TAG, message);
                    }

                });

        return observable;

    }

    public static Disposable createObservableForSensorAccelerometer(Context context){

        Disposable observable = new ReactiveSensors(context).observeSensor(Sensor.TYPE_ACCELEROMETER)
                .subscribeOn(Schedulers.computation())
                .filter(ReactiveSensorFilter.filterSensorChanged())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReactiveSensorEvent>() {

                    @Override
                    public void accept(ReactiveSensorEvent reactiveSensorEvent) throws Exception {
                        SensorEvent event = reactiveSensorEvent.getSensorEvent();

                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];

                        String message = String.format("Accelerometer: x = %f, y = %f, z = %f", x, y, z);
                        Log.d(LOG_TAG, message);
                    }

                });

        return observable;

    }




}
