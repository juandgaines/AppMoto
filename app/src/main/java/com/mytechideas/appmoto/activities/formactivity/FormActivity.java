package com.mytechideas.appmoto.activities.formactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.mytechideas.appmoto.MainActivity;
import com.mytechideas.appmoto.R;
import com.mytechideas.appmoto.activities.dashboardactivity.DashBoardActivity;
import com.mytechideas.appmoto.activities.formactivity.adapter.FormAdapterViewPager;
import com.mytechideas.appmoto.preferences.PrefMang;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;

public class FormActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =101 ;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs_for_viewpager)
    TabLayout tabLayout;
    @BindView(R.id.next_button)
    Button mNextButton;
    @BindView(R.id.previous_button)
    Button mPreviousButton;

    private FormAdapterViewPager pagerAdapter;

    private  int mCurrentPage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        else{
            pagerAdapter =new FormAdapterViewPager(getSupportFragmentManager());
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

            setBackButtonListerner();

            setNextButtonListener();

            setViewPagerChangeListener();
        }



    }

    private void setBackButtonListerner() {
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentPage-=1;
                if(mCurrentPage<0){
                    mCurrentPage=0;
                }
                viewPager.setCurrentItem(mCurrentPage);
                tabLayout.getTabAt(mCurrentPage).select();
            }
        });
    }

    private void setNextButtonListener() {

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mCurrentPage==0){

                    boolean dataCompleted=pagerAdapter.validateData();
                    if (dataCompleted){
                        viewPager.setCurrentItem(++mCurrentPage);
                    }
                    else{

                        Toast.makeText(FormActivity.this,"Todos los campos deben ser diligenciados para continuar.",Toast.LENGTH_LONG).show();
                    }
                }
                else if(mCurrentPage>=1){
                    boolean contactsCompleted=pagerAdapter.validateContacts();
                    if(contactsCompleted) {
                        //PrefMang.setIsFirstTimeApp(false);
                        Intent intent = new Intent(FormActivity.this, DashBoardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(FormActivity.this,"Al menos debes seleccionar 3 contactos de emergencia para continuar",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void setViewPagerChangeListener() {
        ViewPager.OnPageChangeListener viewListener= new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                mCurrentPage=i;
                switch (mCurrentPage){
                    case 0:
                        mNextButton.setEnabled(true);
                        mPreviousButton.setEnabled(false);
                        mPreviousButton.setVisibility(View.INVISIBLE);
                        mNextButton.setText(R.string.next);
                        mPreviousButton.setText("");
                        tabLayout.getTabAt(mCurrentPage).select();
                        break;
                    case 1:

                        boolean x=pagerAdapter.validateData();

                        if(!x){
                            mCurrentPage-=1;
                            viewPager.setCurrentItem(mCurrentPage);
                            viewPager.clearOnPageChangeListeners();
                            tabLayout.getTabAt(mCurrentPage).select();

                        }
                        else {

                            mNextButton.setEnabled(true);
                            mPreviousButton.setEnabled(true);
                            mPreviousButton.setVisibility(View.VISIBLE);
                            mNextButton.setText(R.string.finish);
                            mPreviousButton.setText(R.string.previous);
                            tabLayout.getTabAt(mCurrentPage).select();
                        }
                        break;
                    default:

                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        };

        viewPager.setOnPageChangeListener(viewListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pagerAdapter =new FormAdapterViewPager(getSupportFragmentManager());
                    viewPager.setAdapter(pagerAdapter);
                    tabLayout.setupWithViewPager(viewPager);

                    setBackButtonListerner();

                    setNextButtonListener();

                    setViewPagerChangeListener();

                } else {
                    finish();
                    Toast.makeText(this,"Se necesitan permiso de lectura de contactos para continuar",Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }

    }
}
