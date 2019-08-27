package com.mytechideas.appmoto.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TripEntryWithAccAndGyro {

        @Embedded
        public TripEntry tripEntryntry;

        @Relation(parentColumn = "id",
                entityColumn = "trip_id") public List<GyroscopeEntry> gyroList;

        @Relation(parentColumn = "id",
                entityColumn = "trip_id")public List<AccelerometerEntry> accList;


}
