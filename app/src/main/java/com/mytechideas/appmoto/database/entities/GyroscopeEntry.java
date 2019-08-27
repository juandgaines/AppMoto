package com.mytechideas.appmoto.database.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "gyroscope_data")
public class GyroscopeEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int trip_id;
    private Long sample_time_gyro;
    private Float value_x_gyro;
    private Float value_y_gyro;
    private Float value_z_gyro;
    private Double value_abs_gyro;


    @Ignore
    public GyroscopeEntry(int trip_id,Long sample_time_gyro, Float value_x_gyro, Float value_y_gyro, Float value_z_gyro,Double value_abs_gyro){
        this.trip_id=trip_id;
        this.sample_time_gyro =sample_time_gyro;
        this.value_x_gyro=value_x_gyro;
        this.value_y_gyro=value_y_gyro;
        this.value_z_gyro=value_z_gyro;
        this.value_abs_gyro=value_abs_gyro;

    }

    public GyroscopeEntry(int id,int trip_id,Long sample_time_gyro, Float value_x_gyro, Float value_y_gyro, Float value_z_gyro,Double value_abs_gyro){
        this.id=id;
        this.trip_id=trip_id;
        this.sample_time_gyro =sample_time_gyro;
        this.value_x_gyro=value_x_gyro;
        this.value_y_gyro=value_y_gyro;
        this.value_z_gyro=value_z_gyro;
        this.value_abs_gyro=value_abs_gyro;

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

    public void setSample_time_gyro(Long sample_time_gyro) {
        this.sample_time_gyro = sample_time_gyro;
    }

    public Long getSample_time_gyro() {
        return sample_time_gyro;
    }

    public void setValue_x_gyro(Float value_x_acc) {
        this.value_x_gyro = value_x_acc;
    }

    public Float getValue_x_gyro() {
        return value_x_gyro;
    }

    public void setValue_y_gyro(Float value_y_acc) {
        this.value_y_gyro = value_y_acc;
    }

    public Float getValue_y_gyro() {
        return value_y_gyro;
    }

    public void setValue_z_gyro(Float value_z_acc) {
        this.value_z_gyro = value_z_acc;
    }

    public Float getValue_z_gyro() {
        return value_z_gyro;
    }

    public void setValue_abs_gyro(Double value_abs_gyro) {
        this.value_abs_gyro = value_abs_gyro;
    }

    public Double getValue_abs_gyro() {
        return value_abs_gyro;
    }
}
