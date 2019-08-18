package com.mytechideas.appmoto.database.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.mytechideas.appmoto.database.entities.TripEntryWithAccAndGyro;

import java.util.List;

@Dao
public interface TripWithAccAndGyroDAO {

    @Query("SELECT * from trips")
    List<TripEntryWithAccAndGyro> getAllDataOfTrip();
}
