package com.mytechideas.appmoto.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.mytechideas.appmoto.R;
import com.mytechideas.appmoto.context.AppMotoContext;
import com.mytechideas.appmoto.models.ContactsMoto;

public class PrefMang {

    public static Context context= AppMotoContext.getAppContext();

    private static final Object LOCK =new Object();

    private static SharedPreferences sInstance;

    private static SharedPreferences getSharedPreferenceInstance(){
        if(sInstance==null){
            synchronized (LOCK){
                sInstance= PreferenceManager.getDefaultSharedPreferences(context);
            }

        }
        return sInstance;
    }


    public static Boolean getIsFirstTimeApp(){

        return getSharedPreferenceInstance().getBoolean(PrefConsts.IS_FIRST_TIME_ON_APP,true);
    }

    public static void setIsFirstTimeApp(Boolean value ){
        getSharedPreferenceInstance().edit().putBoolean(PrefConsts.IS_FIRST_TIME_ON_APP,value).apply();
    }

    public static GoogleSignInAccount getSession(){
        Gson gson = new Gson();
        String json = getSharedPreferenceInstance().getString(PrefConsts.USER_SESSION, "");
        GoogleSignInAccount account= gson.fromJson(json, GoogleSignInAccount.class);
        return account;
    }

    public static void setSession(GoogleSignInAccount value ){
        Gson gson= new Gson();
        String json= gson.toJson(value);
        getSharedPreferenceInstance().edit().putString(PrefConsts.USER_SESSION,json).apply();
    }


    public static Boolean getTest(){

        return getSharedPreferenceInstance().getBoolean(PrefConsts.TEST_SERVICE,true);
    }

    public static void setTest(){

        if(getTest()){
            getSharedPreferenceInstance().edit().putBoolean(PrefConsts.TEST_SERVICE,false).apply();
        }
        else{
            getSharedPreferenceInstance().edit().putBoolean(PrefConsts.TEST_SERVICE,true).apply();
        }
    }


    public static void setBirthdayDate(String s) {
        getSharedPreferenceInstance().edit().putString(PrefConsts.USER_BIRTH_DATE,s).apply();
    }

    public static String getBirthdayDate() {
        return getSharedPreferenceInstance().getString(PrefConsts.USER_BIRTH_DATE, "");
    }

    public static void setBlodType(String s) {

        String[] list= context.getResources().getStringArray(R.array.blod_array);

        if(s.equals(list[0])){
            getSharedPreferenceInstance().edit().putString(PrefConsts.USER_BLOOD,"").apply();
        }
        else {
            getSharedPreferenceInstance().edit().putString(PrefConsts.USER_BLOOD, s).apply();
        }
    }

    public static String getBlodType() {

        String blood="";
        blood=getSharedPreferenceInstance().getString(PrefConsts.USER_BLOOD, "");
        String[] list= context.getResources().getStringArray(R.array.blod_array);
        if(blood.equals(list[0])){
            return "";
        }
        else{
            return blood;
        }

    }

    public static void setContact(int mSelectedContacts, ContactsMoto contactsMoto) {
        Gson gson= new Gson();

        String json= "";
        if (contactsMoto!=null){
            json=gson.toJson(contactsMoto);
        }

        switch (mSelectedContacts) {
            case 0:
                getSharedPreferenceInstance().edit().putString(PrefConsts.USER_CONTACT1, json).apply();
                break;
            case 1:
                getSharedPreferenceInstance().edit().putString(PrefConsts.USER_CONTACT2, json).apply();
                break;
            case 2:
                getSharedPreferenceInstance().edit().putString(PrefConsts.USER_CONTACT3, json).apply();
                break;
            default:
                break;
        }

    }

    public static ContactsMoto getContact(int position) {
        Gson gson= new Gson();

        String json= "";

        switch (position) {
            case 0:
                json=getSharedPreferenceInstance().getString(PrefConsts.USER_CONTACT1, "");
                break;
            case 1:
                json=getSharedPreferenceInstance().getString(PrefConsts.USER_CONTACT2, "");
                break;
            case 2:
                json=getSharedPreferenceInstance().getString(PrefConsts.USER_CONTACT3, "");
                break;
            default:
                break;
        }
        ContactsMoto contact= gson.fromJson(json, ContactsMoto.class);

        return contact;
    }


    public static int getSpotOfContact(ContactsMoto contactsMoto) {

        int position=PrefConsts.NO_POSITION;

        for(int i=0; i<3;i++){
            ContactsMoto actualContact= getContact(i);

            if (contactsMoto.equals(actualContact)){
                position=i;
            }
        }
        return position;
    }
}
