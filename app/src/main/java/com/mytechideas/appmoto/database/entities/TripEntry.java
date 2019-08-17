package com.mytechideas.appmoto.database.entities;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "trips")
public class TripEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int user_id;
    private Date start_date;
    private Date end_date;

    @Ignore
    public TripEntry(int user_id,Date start_date, Date end_date){
        this.user_id=user_id;
        this.start_date= start_date;
        this.end_date=end_date;
    }

    public TripEntry(int id,int user_id,Date start_date, Date end_date){
        this.id=id;
        this.user_id=user_id;
        this.start_date= start_date;
        this.end_date=end_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getEnd_date() {
        return end_date;
    }


}
