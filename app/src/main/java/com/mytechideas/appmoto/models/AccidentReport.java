package com.mytechideas.appmoto.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AccidentReport {

    @SerializedName("last_location")
    private LastLocationKnown lastLocationKnown;
    @SerializedName("contacts_to_report")
    private FavoriteContactsUser favoriteContactsUsers;


    public AccidentReport(LastLocationKnown lastLocationKnown, FavoriteContactsUser favoriteContactsUsers){
        this.lastLocationKnown=lastLocationKnown;
        this.favoriteContactsUsers=favoriteContactsUsers;
    }

    public FavoriteContactsUser getFavoriteContactsUsers() {
        return favoriteContactsUsers;
    }

    public LastLocationKnown getLastLocationKnown() {
        return lastLocationKnown;
    }

    public void setFavoriteContactsUsers(FavoriteContactsUser favoriteContactsUsers) {
        this.favoriteContactsUsers = favoriteContactsUsers;
    }

    public void setLastLocationKnown(LastLocationKnown lastLocationKnown) {
        this.lastLocationKnown = lastLocationKnown;
    }
}
