package com.mytechideas.appmoto.database.daos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mytechideas.appmoto.database.entities.TripEntry;

import java.util.List;

@Dao
public interface TripsDAO {

    @Query("SELECT * FROM trips ORDER BY start_date")
    List<TripEntry> loadAllTrips();

    @Insert
    void insertTrip(TripEntry tripEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTrip(TripEntry tripEntry);

    @Delete
    void deleteTask(TripEntry tripEntry);
}
