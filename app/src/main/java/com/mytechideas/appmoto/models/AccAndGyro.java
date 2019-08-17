package com.mytechideas.appmoto.models;

public class AccAndGyro {

    private float accX;
    private float accY;
    private float accZ;
    private float gyroX;
    private float gyroY;
    private float gyroZ;


    public AccAndGyro(float accX, float accY, float accZ, float gyroX, float gyroY, float gyroZ) {
        this.accX=accX;
        this.accY= accY;
        this.accZ=accZ;
        this.gyroX=gyroX;
        this.gyroY=gyroY;
        this.gyroZ=gyroZ;
    }

    public void setAccX(float accX) {
        this.accX = accX;
    }

    public void setAccY(float accY) {
        this.accY = accY;
    }

    public void setAccZ(float accZ) {
        this.accZ = accZ;
    }

    public void setGyroX(float gyroX) {
        this.gyroX = gyroX;
    }

    public void setGyroY(float gyroY) {
        this.gyroY = gyroY;
    }

    public void setGyroZ(float gyroZ) {
        this.gyroZ = gyroZ;
    }

    public float getAccX() {
        return accX;
    }

    public float getAccY() {
        return accY;
    }

    public float getAccZ() {
        return accZ;
    }

    public float getGyroX() {
        return gyroX;
    }

    public float getGyroY() {
        return gyroY;
    }

    public float getGyroZ() {
        return gyroZ;
    }
}
