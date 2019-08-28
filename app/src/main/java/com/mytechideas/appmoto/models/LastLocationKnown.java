package com.mytechideas.appmoto.models;

public class LastLocationKnown {

    public String user_id;
    public Integer trip_id;
    public Double latitude;
    public Double longitude;

    public LastLocationKnown(String user_id,Integer trip_id,double latitude, double longitude) {
        this.user_id=user_id;
        this.latitude=latitude;
        this.longitude=longitude;
        this.trip_id=trip_id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getTrip_id() {
        return trip_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setTrip_id(Integer trip_id) {
        this.trip_id = trip_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
