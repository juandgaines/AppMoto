package com.mytechideas.appmoto.models;

public class ContactsMoto {

    private String mName="";
    private String mPhone="";
    private Boolean selected=false;

    public ContactsMoto(String name, String phone){
        mName=name;
        mPhone=phone;
        selected=false;
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
