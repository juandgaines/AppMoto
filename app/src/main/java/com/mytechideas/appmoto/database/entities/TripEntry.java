package com.mytechideas.appmoto.database.entities;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "trips")
public class TripEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date start_date;
    private Date end_date;

    @Ignore
    public TripEntry(Date mStart, Date mFinal){


    }

}
