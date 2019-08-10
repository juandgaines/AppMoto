package com.mytechideas.appmoto.activities.formactivity.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mytechideas.appmoto.R;
import com.mytechideas.appmoto.activities.formactivity.fragments.ContactsDataFragment;
import com.mytechideas.appmoto.activities.formactivity.fragments.SimpleDataFragment;
import com.mytechideas.appmoto.context.AppMotoContext;
import com.mytechideas.appmoto.preferences.PrefMang;

public class FormAdapterViewPager extends FragmentPagerAdapter {


    private Context context= AppMotoContext.getAppContext();

    public FormAdapterViewPager(FragmentManager fm) {
        super(fm);

    }

    public void validate(){

    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return new SimpleDataFragment();
            case 1:
                return new ContactsDataFragment();

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

        return true;
    }

    public boolean validateData() {

        if(PrefMang.getSession().equals("") || PrefMang.getBirthdayDate().equals("") || PrefMang.getBlodType().equals("") )
            return false;
        else {
            return true;
        }
    }
}
