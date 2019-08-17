package com.mytechideas.appmoto.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.mytechideas.appmoto.database.converters.DateConverter;
import com.mytechideas.appmoto.database.daos.AccDAO;
import com.mytechideas.appmoto.database.daos.GyroDAO;
import com.mytechideas.appmoto.database.daos.TripsDAO;
import com.mytechideas.appmoto.database.entities.AccelerometerEntry;
import com.mytechideas.appmoto.database.entities.GyroscopeEntry;
import com.mytechideas.appmoto.database.entities.TripEntry;

@Database(entities = {TripEntry.class, GyroscopeEntry.class, AccelerometerEntry.class},version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG=AppDatabase.class.getSimpleName();
    private static final Object LOCK= new Object();
    private static final String DATABASE_NAME= "appmoto_database";

    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context){

        if(sInstance==null){

            synchronized (LOCK){
                Log.d(LOG_TAG,"Creating new database instance");
                sInstance= Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,AppDatabase.DATABASE_NAME)
                        //Remove this line when you made sure the DB is working.
                        //.allowMainThreadQueries()
                        .build();
            }
        }
        Log.v(LOG_TAG,"Getting database instance");
        return sInstance;
    }

    public abstract TripsDAO tripsDao();
    public abstract AccDAO accDAO();
    public abstract GyroDAO gyroDAO();




}