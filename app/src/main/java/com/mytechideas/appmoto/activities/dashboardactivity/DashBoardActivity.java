package com.mytechideas.appmoto.activities.dashboardactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.pwittchen.reactivesensors.library.ReactiveSensorEvent;
import com.github.pwittchen.reactivesensors.library.ReactiveSensorFilter;
import com.github.pwittchen.reactivesensors.library.ReactiveSensors;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.mytechideas.appmoto.MainActivity;
import com.mytechideas.appmoto.activities.formactivity.adapter.FormAdapterViewPager;
import com.mytechideas.appmoto.database.AppDatabase;
import com.mytechideas.appmoto.database.converters.DateConverter;
import com.mytechideas.appmoto.database.entities.TripEntry;
import com.mytechideas.appmoto.database.entities.TripEntryWithAccAndGyro;
import com.mytechideas.appmoto.database.executors.AppExecutors;
import com.mytechideas.appmoto.preferences.PrefMang;
import com.mytechideas.appmoto.R;
import com.mytechideas.appmoto.services.MotoBackgroundService;
import com.mytechideas.appmoto.services.MotoBackgroundTasks;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DashBoardActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String LOG_TAG=DashBoardActivity.class.getSimpleName();

    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 102;
    private GoogleSignInClient mGoogleSignInClient;
    private FusedLocationProviderClient fusedLocationClient;
    private Location mLocation;

    @BindView(R.id.start)
    Button mStartButton;
    private AppDatabase mDb;

    private boolean state=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        mDb= AppDatabase.getsInstance(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_LOCATION);
        } else {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestId()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            if (PrefMang.getIsFirstTimeApp()) {
                Toast.makeText(this, "FirstTimeOnApp", Toast.LENGTH_LONG).show();
                //PrefMang.setIsFirstTimeApp(false);
            }

            mStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (state==false) {
                        state=true;
                        mStartButton.setText("Detener");
                        Intent intent = new Intent(DashBoardActivity.this, MotoBackgroundService.class);
                        intent.setAction(MotoBackgroundTasks.ACTION_SEND_SENSORS);
                        startService(intent);
                    }else{
                        state=false;
                        mStartButton.setText("Iniciar");
                        Intent intent = new Intent(DashBoardActivity.this, MotoBackgroundService.class);
                        intent.setAction(MotoBackgroundTasks.ACTION_STOP_SENSORS);
                        startService(intent);
                    }
                }
            });
            //SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
            //sp.registerOnSharedPreferenceChangeListener(this);
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                signOut();
                return true;
            case R.id.send_alert:
                sendLastLocation();
                return true;
            case R.id.debug_db:
                getDataFromDB();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getDataFromDB() {

        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             List<TripEntryWithAccAndGyro> x = mDb.tripWithAccAndGyroDAO().getAllDataOfTrip();
                                                             Gson gson= new Gson();
                                                             String json= gson.toJson(x);
                                                         }

                                                     }
        );
    }

    private void sendLastLocation() {
        fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    mLocation=location;
                                    Uri gmmIntentUri = Uri.parse("geo:"+mLocation.getLatitude()+","+mLocation.getLongitude());
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(mapIntent);
                                    }
                                }
                            }
                        });
    }

    private void signOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .requestScopes(new Scope("https://www.googleapis.com/auth/user.birthday.read"))
                            .requestId()
                            .build();

                    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

                    if (PrefMang.getIsFirstTimeApp()){
                        Toast.makeText(this,"FirstTimeOnApp",Toast.LENGTH_LONG).show();
                        //PrefMang.setIsFirstTimeApp(false);
                    }


                } else {
                    finish();
                    Toast.makeText(this,"Se necesitan permiso de lectura localizacion para continuar",Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }

}
