package com.mytechideas.appmoto.activities.formactivity.adapter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.gson.Gson;
import com.mytechideas.appmoto.R;
import com.mytechideas.appmoto.activities.formactivity.fragments.ContactsDataFragment;
import com.mytechideas.appmoto.activities.formactivity.fragments.SimpleDataFragment;
import com.mytechideas.appmoto.context.AppMotoContext;
import com.mytechideas.appmoto.models.ContactsMoto;
import com.mytechideas.appmoto.models.FavoriteContactsUser;
import com.mytechideas.appmoto.models.RegisterUser;
import com.mytechideas.appmoto.network.AppMotoRetrofitinstance;
import com.mytechideas.appmoto.preferences.PrefMang;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormAdapterViewPager extends FragmentPagerAdapter {


    private Context context= AppMotoContext.getAppContext();

    SimpleDataFragment simpleDataFragment=new SimpleDataFragment();
    ContactsDataFragment contactsDataFragment=new ContactsDataFragment();


    public FormAdapterViewPager(FragmentManager fm) {
        super(fm);

    }

    public void validate(){

    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return simpleDataFragment;
            case 1:
                return contactsDataFragment;

                default: return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return context.getString(R.string.basic_data_tab_title);

            case 1:
                return context.getString(R.string.contacts_tab_title);

            default: return "";

        }
    }

    public Boolean validateContacts(){
        ArrayList<ContactsMoto>contactsMotos= contactsDataFragment.getContactSelected();
        FavoriteContactsUser favoriteContactsUser= new FavoriteContactsUser( PrefMang.getSession().getId(),contactsMotos);
        Gson gson= new Gson();
        String json= gson.toJson(favoriteContactsUser);

        if(!contactsMotos.isEmpty()){
            PrefMang.setContacts(favoriteContactsUser);
            AppMotoRetrofitinstance.getAppMotoService().sendAlertToContacts(favoriteContactsUser).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context, context.getText(R.string.registered_contacts_successfull), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, context.getText(R.string.registered_contacts_failed), Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            //TODO logic for message indicating that you should add the contacts later.
        }
        return true;
    }

    public boolean validateData() {

        if(PrefMang.getSession().equals("") || PrefMang.getBirthdayDate().equals("") || PrefMang.getBlodType().equals("") || simpleDataFragment.getBrand().equals(""
        )||simpleDataFragment.getModel().equals("") || simpleDataFragment.getPlaca().equals("") || simpleDataFragment.getReference().equals("")) {
            return false;
        }

        else {
            RegisterUser registerUser= new RegisterUser(PrefMang.getSession().getId(),PrefMang.getBirthDate(),PrefMang.getBlodType(),
                    simpleDataFragment.getBrand(), simpleDataFragment.getReference(), simpleDataFragment.getModel(), simpleDataFragment.getPlaca());
            Gson gson= new Gson();
            String json= gson.toJson(registerUser);

            AppMotoRetrofitinstance.getAppMotoService().registerUser( registerUser).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Response<Void> r = response;
                    Toast.makeText(context, context.getText(R.string.register_successfull), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, context.getText(R.string.register_failed), Toast.LENGTH_LONG).show();
                }
            });

            return true;


        }
    }
}
