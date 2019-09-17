package com.mytechideas.appmoto.network;

import com.mytechideas.appmoto.database.entities.TripEntryWithAccAndGyro;
import com.mytechideas.appmoto.models.FavoriteContactsUser;
import com.mytechideas.appmoto.models.RegisterUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AppMotoServiceCall {

    @POST("usuario/register")
    Call<Void> registerUser(@Body RegisterUser registerUser);

    @POST("alert/emergency_contacts")
    Call<Void> sendAlertToContacts(@Body FavoriteContactsUser favoriteContactsUser);

    @POST("trip/register")
    Call<Void> registerTrip(@Body List<TripEntryWithAccAndGyro> tripEntryWithAccAndGyro);
}
