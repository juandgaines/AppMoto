package com.mytechideas.appmoto.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "accelerometer_data")
public class AccelerometerEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int trip_id;
    private int user_id;
    private int sample_time;
    private float value_x_acc;
    private float value_y_acc;
    private float value_z_acc;


    @Ignore
    public AccelerometerEntry(int mTripId, int mUserId,int mSampleTime, float mValueX, float mValueY, float mValueZ){
        this.trip_id=mTripId;
        this.user_id=mUserId;
        this.sample_time=mSampleTime;
        this.value_x_acc=mValueX;
        this.value_y_acc=mValueY;
        this.value_z_acc=mValueZ;

    }

    public AccelerometerEntry(int mId,int mTripId, int mUserId,int mSampleTime, float mValueX, float mValueY, float mValueZ){
        this.id=mId;
        this.trip_id=mTripId;
        this.user_id=mUserId;
        this.sample_time=mSampleTime;
        this.value_x_acc=mValueX;
        this.value_y_acc=mValueY;
        this.value_z_acc=mValueZ;

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

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setSample_time(int sample_time) {
        this.sample_time = sample_time;
    }

    public int getSample_time() {
        return sample_time;
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
