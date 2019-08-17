package com.mytechideas.appmoto.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mytechideas.appmoto.database.entities.AccelerometerEntry;

import java.util.List;

@Dao
public interface AccDAO {

    //Accelerometer DAO
    @Query("SELECT * FROM accelerometer_data ORDER BY sample_time_acc")
    List<AccelerometerEntry> loadAllAccelerometerData();

    @Query("SELECT * FROM accelerometer_data WHERE trip_id=:id ORDER BY sample_time_acc ASC ")
    List<AccelerometerEntry> loadAllAccelerometerDataByTripId(int id);

    @Insert
    long insertAccelerometerSample(AccelerometerEntry accelerometerEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAcc(AccelerometerEntry accelerometerEntry);

    @Delete
    void deleteAcc(AccelerometerEntry accelerometerEntry);

    @Query("DELETE FROM accelerometer_data")
    void deleteAllAccSamples();
}
