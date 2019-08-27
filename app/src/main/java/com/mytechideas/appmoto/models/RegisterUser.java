package com.mytechideas.appmoto.models;

import java.util.Date;

public class RegisterUser {

    private String user_id="";
    private Date birth_date=null;
    private String blod_type="";
    private String brand="";
    private String reference="";
    private String model="";
    private String placa="";

    public RegisterUser(String user_id, Date birth_date, String blod_type, String brand, String reference, String model, String placa){
        this.user_id=user_id;
        this.birth_date=birth_date;
        this.blod_type=blod_type;
        this.brand=brand;
        this.reference=reference;
        this.model=model;
        this.placa=placa;
    }

    public String getUser_id() {
        return user_id;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public String getBlod_type() {
        return blod_type;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getPlaca() {
        return placa;
    }

    public String getReference() {
        return reference;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setBlod_type(String blod_type) {
        this.blod_type = blod_type;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
