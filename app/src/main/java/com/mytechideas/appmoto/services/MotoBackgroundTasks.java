package com.mytechideas.appmoto.services;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.github.pwittchen.reactivesensors.library.ReactiveSensorEvent;
import com.github.pwittchen.reactivesensors.library.ReactiveSensorFilter;
import com.github.pwittchen.reactivesensors.library.ReactiveSensors;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.mytechideas.appmoto.R;
import com.mytechideas.appmoto.activities.dashboardactivity.DashBoardActivity;
import com.mytechideas.appmoto.context.AppMotoContext;
import com.mytechideas.appmoto.database.AppDatabase;
import com.mytechideas.appmoto.database.daos.TripWithAccAndGyroDAO;
import com.mytechideas.appmoto.database.entities.AccelerometerEntry;
import com.mytechideas.appmoto.database.entities.GyroscopeEntry;
import com.mytechideas.appmoto.database.entities.TripEntry;
import com.mytechideas.appmoto.database.entities.TripEntryWithAccAndGyro;
import com.mytechideas.appmoto.database.executors.AppExecutors;
import com.mytechideas.appmoto.models.AccAndGyro;
import com.mytechideas.appmoto.models.AccidentReport;
import com.mytechideas.appmoto.models.FavoriteContactsUser;
import com.mytechideas.appmoto.models.LastLocationKnown;
import com.mytechideas.appmoto.network.AppMotoRetrofitinstance;
import com.mytechideas.appmoto.preferences.PrefMang;
import com.mytechideas.appmoto.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotoBackgroundTasks {
    public static final String LOG_TAG=MotoBackgroundTasks.class.getSimpleName();

    public static final String STATE_1="state_1";
    public static final String STATE_2="state_2";
    public static final String STATE_3="state_3";

    public static final String ACTION_SEND_SENSORS="send-sensors-to-server";
    public static final String ACTION_STOP_SENSORS="stop-sensors-to-server";
    public static TripEntry entry=null;
    public static double lastloGyroReader=0.0;

    public static double lastloAccReader=0.0;

    public static String state=STATE_1;


    private static CompositeDisposable observablesSensors=new CompositeDisposable();
    private static AppDatabase mDb;
    private static Context mContext;

    private static FusedLocationProviderClient fusedLocationClient;
    private static Location mLocation;

    public static void  executeTasks(Context context, String action){

        mContext=context;
        mDb= AppDatabase.getsInstance(context);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (ACTION_SEND_SENSORS.equals(action)){
            Log.d(LOG_TAG, "Enviando sensores....");
            Date mStartTripDate= new Date();
            entry=new TripEntry(0,mStartTripDate,null);

            AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    long id=mDb.tripsDao().insertTrip(entry);
                    entry.setId((int)id);
                    NotificationUtils.createNotificationMotoCurrentlySharing(context);
                    observablesSensors.add(combineObservablesTest(context));
                }
            });

        }

        else if (ACTION_STOP_SENSORS.equals(action)){
            Log.d(LOG_TAG, "Detener sensores....");

            AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    entry.setEnd_date(new Date());
                    mDb.tripsDao().updateTrip(entry);
                    TripEntry finalStateTrip=mDb.tripsDao ().getTripById((int)entry.getId());
                    List<AccelerometerEntry> accelerometerEntryList= mDb.accDAO().loadAllAccelerometerDataByTripId(entry.getId());
                    List<GyroscopeEntry> gyroscopeEntries=mDb.gyroDAO().loadAllGyroscpeDataByTripId(entry.getId());
                    List<TripEntryWithAccAndGyro> mOverAllData= mDb.tripWithAccAndGyroDAO().getAllDataOfTrip();
                    observablesSensors.clear();
                    NotificationUtils.closeNotification(context);

                    List<TripEntryWithAccAndGyro> x = mDb.tripWithAccAndGyroDAO().getAllDataOfTrip();

                    AppMotoRetrofitinstance.getAppMotoService().registerTrip(x).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            NotificationUtils.createNotificationTripUploaded(context);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            NotificationUtils.createNotificationTripFailUploaded(context);

                        }
                    });
                    mDb.tripsDao().deleteAllTrips();
                    mDb.accDAO().deleteAllAccSamples();
                    mDb.gyroDAO().deleteAllGyroSamples();
                }
            });


        }
    }

    public static Disposable combineObservablesTest(Context context){

        Flowable<ReactiveSensorEvent> observable1 = new ReactiveSensors(context).observeSensor(Sensor.TYPE_ACCELEROMETER)
                .subscribeOn(Schedulers.computation())
                .filter(ReactiveSensorFilter.filterSensorChanged())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposableOperation = new ReactiveSensors(context).observeSensor(Sensor.TYPE_GYROSCOPE)
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


                        int sampleid = entry.getId();
                        long sampleDate = new Date().getTime();

                        double loGyroReader = Math.sqrt(Math.pow(accAndGyro.getGyroX(), 2)
                                + Math.pow(accAndGyro.getGyroY(), 2)
                                + Math.pow(accAndGyro.getGyroZ(), 2));

                        GyroscopeEntry gyroscopeEntry =
                                new GyroscopeEntry(sampleid,sampleDate,accAndGyro.getGyroX(),accAndGyro.getGyroY(),accAndGyro.getGyroZ(),loGyroReader);



                        double loAccReader = Math.sqrt(Math.pow(accAndGyro.getAccX(), 2)
                                + Math.pow(accAndGyro.getAccY(), 2)
                                + Math.pow(accAndGyro.getAccZ(), 2));

                        AccelerometerEntry accelerometerEntry=
                                new AccelerometerEntry(sampleid,sampleDate,
                                        accAndGyro.getAccX(),accAndGyro.getAccY(),accAndGyro.getAccZ(),loAccReader);


                        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                                                                         @Override
                                                                         public void run() {
                                                                             mDb.accDAO().insertAccelerometerSample(accelerometerEntry);
                                                                             mDb.gyroDAO().insertGyroscopeSample(gyroscopeEntry);
                                                                         }
                                                                     }
                        );


                        if(state.equals(STATE_1)){
                            if(Math.abs(loAccReader-lastloAccReader) >5){
                                state=STATE_2;
                            }
                        }
                        else if(state.equals(STATE_2) ){
                            if(Math.abs(loAccReader-lastloAccReader) >10 && loGyroReader>10){
                                state=STATE_3;
                            }
                        }
                        else if(state.equals(STATE_3)){
                            Log.d("emergency","Estallado....");
                            NotificationUtils.createNotificationMotoAccident(context);
                            fusedLocationClient.getLastLocation()
                                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            // Got last known location. In some rare situations this can be null.
                                            if (location != null) {
                                                mLocation=location;

                                                LastLocationKnown lastLocationKnown=new LastLocationKnown(
                                                        PrefMang.getSession().getId(),sampleid
                                                        ,mLocation.getLatitude(),mLocation.getLongitude());

                                                FavoriteContactsUser contactForEmergency = PrefMang.getContacts();
                                                AccidentReport accidentReport = new AccidentReport(lastLocationKnown,contactForEmergency);

                                                AppMotoRetrofitinstance.getAppMotoService().sendAlertToContacts(accidentReport).enqueue(new Callback<Void>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        //Toast.makeText(DashBoardActivity.this,"Accidente reportado",Toast.LENGTH_LONG);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Void> call, Throwable t) {
                                                        //Toast.makeText(DashBoardActivity.this,"No se pudo reportar emergencia. Intentalo de nuevo.",Toast.LENGTH_LONG);
                                                    }
                                                });
                                            }
                                        }
                                    });
                            state="";

                        }
                    }
                });


        return disposableOperation;
    }
}
