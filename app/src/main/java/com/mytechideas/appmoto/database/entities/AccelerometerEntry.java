package com.mytechideas.appmoto.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "accelerometer_data")
public class AccelerometerEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int trip_id;
    private long sample_time_acc;
    private float value_x_acc;
    private float value_y_acc;
    private float value_z_acc;


    @Ignore
    public AccelerometerEntry(int trip_id,long sample_time_acc, float value_x_acc, float value_y_acc, float value_z_acc){
        this.trip_id=trip_id;
        this.sample_time_acc =sample_time_acc;
        this.value_x_acc=value_x_acc;
        this.value_y_acc=value_y_acc;
        this.value_z_acc=value_z_acc;

    }

    public AccelerometerEntry(int id,int trip_id,long sample_time_acc, float value_x_acc, float value_y_acc, float value_z_acc){
        this.id=id;
        this.trip_id=trip_id;
        this.sample_time_acc =sample_time_acc;
        this.value_x_acc=value_x_acc;
        this.value_y_acc=value_y_acc;
        this.value_z_acc=value_z_acc;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public int getTrip_id() {
        return trip_id;
    }


    public void setSample_time_acc(long sample_time_acc) {
        this.sample_time_acc = sample_time_acc;
    }

    public long getSample_time_acc() {
        return sample_time_acc;
    }

    public void setValue_x_acc(float value_x_acc) {
        this.value_x_acc = value_x_acc;
    }

    public float getValue_x_acc() {
        return value_x_acc;
    }

    public void setValue_y_acc(float value_y_acc) {
        this.value_y_acc = value_y_acc;
    }

    public float getValue_y_acc() {
        return value_y_acc;
    }

    public void setValue_z_acc(float value_z_acc) {
        this.value_z_acc = value_z_acc;
    }

    public float getValue_z_acc() {
        return value_z_acc;
    }
}
