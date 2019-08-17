package com.mytechideas.appmoto.database.daos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mytechideas.appmoto.database.entities.AccelerometerEntry;
import com.mytechideas.appmoto.database.entities.GyroscopeEntry;

import java.util.List;

@Dao
public interface GyroDAO {

    //Accelerometer DAO
    @Query("SELECT * FROM accelerometer_data ORDER BY sample_time_acc")
    List<GyroscopeEntry> loadAllGyroscopeData();

    @Query("SELECT * FROM accelerometer_data WHERE trip_id=:id ORDER BY sample_time_acc DESC ")
    List<GyroscopeEntry> loadAllGyroscpeDataByTripId(int id);

    @Insert
    long insertGyroscopeSample(GyroscopeEntry gyroscopeEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateGyro(GyroscopeEntry gyroscopeEntry);

    @Delete
    void deleteGyro(GyroscopeEntry gyroscopeEntry);

    @Query("DELETE FROM gyroscope_data")
    void deleteAllGyroSamples();
}
