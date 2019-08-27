package com.mytechideas.appmoto.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FavoriteContactsUser {

    @SerializedName("user_id")
    String user_id="";
    @SerializedName("array_contacts")
    private ArrayList<ContactsMoto> array_contacts=new ArrayList<>();

    public FavoriteContactsUser(String id, ArrayList<ContactsMoto> array_contacts){
        this.user_id=id;
        this.array_contacts=array_contacts;
    }

    public void setArray_contacts(ArrayList<ContactsMoto> array_contacts) {
        this.array_contacts = array_contacts;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<ContactsMoto> getArray_contacts() {
        return array_contacts;
    }

    public String getUser_id() {
        return user_id;
    }
}
