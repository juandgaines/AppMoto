package com.mytechideas.appmoto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactsMoto {

    @SerializedName("name")
    private String mName="";
    @SerializedName("id")
    private Integer mId=-1;
    @SerializedName("phone")
    private String mPhone="";
    @Expose(serialize = false,deserialize = false)
    private Boolean selected=false;

    public ContactsMoto(String name, String phone){
        mName=name;
        mPhone=phone;
        selected=false;
    }
    public ContactsMoto(Integer id , String name, String phone){
        mId=id;
        mName=name;
        mPhone=phone;
        selected=false;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
