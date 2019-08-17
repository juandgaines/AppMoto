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
import com.mytechideas.appmoto.database.AppDatabase;
import com.mytechideas.appmoto.database.entities.AccelerometerEntry;
import com.mytechideas.appmoto.database.entities.GyroscopeEntry;
import com.mytechideas.appmoto.database.entities.TripEntry;
import com.mytechideas.appmoto.database.executors.AppExecutors;
import com.mytechideas.appmoto.models.AccAndGyro;
import com.mytechideas.appmoto.preferences.PrefMang;
import com.mytechideas.appmoto.utils.NotificationUtils;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MotoBackgroundTasks {
    public static final String LOG_TAG=MotoBackgroundTasks.class.getSimpleName();

    public static final String ACTION_SEND_SENSORS="send-sensors-to-server";
    public static final String ACTION_STOP_SENSORS="stop-sensors-to-server";
    public static TripEntry entry=null;


    private static CompositeDisposable observablesSensors=new CompositeDisposable();
    private static AppDatabase mDb;

    public static void  executeTasks(Context context, String action){

        mDb= AppDatabase.getsInstance(context);

        if (ACTION_SEND_SENSORS.equals(action)){
            Log.d(LOG_TAG, "Enviando sensores....");
            Date mStartTripDate= new Date();
            entry=new TripEntry(0,mStartTripDate,null);
            long id=mDb.tripsDao().insertTrip(entry);
            entry.setId((int)id);
            NotificationUtils.createNotificationMotoCurrentlySharing(context);
            combineObservablesTest(context);
            //observablesSensors.add(createObservableForSensorGyroscope(context));
            //observablesSensors.add(createObservableForSensorAccelerometer(context));
        }

        else if (ACTION_STOP_SENSORS.equals(action)){
            Log.d(LOG_TAG, "Detener sensores....");

            entry.setEnd_date(new Date());
            mDb.tripsDao().updateTrip(entry);
            TripEntry finalStateTrip=mDb.tripsDao ().getTripById((int)entry.getId());

            List<AccelerometerEntry> accelerometerEntryList= mDb.accDAO().loadAllAccelerometerDataByTripId(entry.getId());
            List<GyroscopeEntry> gyroscopeEntries=mDb.gyroDAO().loadAllGyroscpeDataByTripId(entry.getId());


            observablesSensors.clear();
            NotificationUtils.closeNotification(context);

            mDb.tripsDao().deleteAllTrips();
            mDb.accDAO().deleteAllAccSamples();
            mDb.gyroDAO().deleteAllGyroSamples();
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

                        GyroscopeEntry gyroscopeEntry =
                                new GyroscopeEntry(entry.getId(),new Date().getTime(),x,y,z);


                        AppExecutors.getsInstance().diskIO().execute(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        mDb.gyroDAO().insertGyroscopeSample(gyroscopeEntry);
                                    }
                                }
                        );

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

                        AccelerometerEntry accelerometerEntry=
                                new AccelerometerEntry(entry.getId(),new Date().getTime(),x,y,z);


                        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                                                                         @Override
                                                                         public void run() {
                                                                             mDb.accDAO().insertAccelerometerSample(accelerometerEntry);
                                                                         }
                                                                     }
                        );

                        Log.d(LOG_TAG, message);
                    }

                });

        return observable;

    }

    public static void combineObservablesTest(Context context){

        Flowable<ReactiveSensorEvent> observable1 = new ReactiveSensors(context).observeSensor(Sensor.TYPE_ACCELEROMETER,1000)
                .subscribeOn(Schedulers.computation())
                .filter(ReactiveSensorFilter.filterSensorChanged())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable observable2 = new ReactiveSensors(context).observeSensor(Sensor.TYPE_GYROSCOPE)
                .subscribeOn(Schedulers.computation())
                .filter(ReactiveSensorFilter.filterSensorChanged())
                .observeOn(AndroidSchedulers.mainThread())
                .withLatestFrom(observable1, new BiFunction<ReactiveSensorEvent, ReactiveSensorEvent, AccAndGyro>() {
                    @Override
                    public AccAndGyro apply(ReactiveSensorEvent reactiveAccEvent, ReactiveSensorEvent reactiveGyroEvent) throws Exception {
                        return new AccAndGyro(
                                reactiveAccEvent.getSensorEvent().values[0],
                                reactiveAccEvent.getSensorEvent().values[1],
                                reactiveAccEvent.getSensorEvent().values[2],
                                reactiveGyroEvent.getSensorEvent().values[0],
                                reactiveGyroEvent.getSensorEvent().values[1],
                                reactiveGyroEvent.getSensorEvent().values[2]

                        );
                    }
                })
                .subscribe(new Consumer<AccAndGyro>() {
                    @Override
                    public void accept(AccAndGyro accAndGyro) throws Exception {


                        GyroscopeEntry gyroscopeEntry =
                                new GyroscopeEntry(entry.getId(),new Date().getTime(),accAndGyro.getGyroX(),accAndGyro.getGyroY(),accAndGyro.getGyroZ());



                        AccelerometerEntry accelerometerEntry=
                                new AccelerometerEntry(entry.getId(),new Date().getTime(),
                                        accAndGyro.getAccX(),accAndGyro.getAccY(),accAndGyro.getAccZ());


                        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                                                                         @Override
                                                                         public void run() {
                                                                             mDb.accDAO().insertAccelerometerSample(accelerometerEntry);
                                                                         }
                                                                     }
                        );

                        AppExecutors.getsInstance().diskIO().execute(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        mDb.gyroDAO().insertGyroscopeSample(gyroscopeEntry);
                                    }
                                }
                        );

                    }
                });



    }




}
