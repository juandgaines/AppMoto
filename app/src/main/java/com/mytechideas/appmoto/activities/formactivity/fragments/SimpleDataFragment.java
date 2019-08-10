package com.mytechideas.appmoto.activities.formactivity.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.mytechideas.appmoto.R;
import com.mytechideas.appmoto.preferences.PrefMang;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleDataFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG= SimpleDataFragment.class.getSimpleName();
    @BindView(R.id.name_view)
    EditText mUserName;
    @BindView(R.id.date_view)
    EditText mDateView;
    @BindView(R.id.blod_spinner)
    Spinner mSpinner;

    private Calendar c;
    private int mYear;
    private int mMonth;
    private int mDay;
    DatePickerDialog datePickerDialog;
    private String mBlod;
    private GridLayoutManager layoutManager;
    private int edad=-1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_simple_data, container, false);

        ButterKnife.bind(this,view);
        c= Calendar.getInstance();


        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

        mUserName.setText(PrefMang.getSession().getDisplayName());
        mUserName.setEnabled(false);
        int Actual=mYear;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.blod_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setOnItemSelectedListener(this);
        mSpinner.setAdapter(adapter);

        mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog= new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        mDateView.setText(year +"/"+ (month+1)+"/"+day);
                        PrefMang.setBirthdayDate(year +"/"+ (month+1)+"/"+day);
                        c.set(year,month,day);
                        mYear=year;
                        edad=Actual-mYear;

                        if(mMonth<month){
                            edad--;
                        }
                        mMonth=month;
                        mDay=day;

                        Log.d(LOG_TAG,"Date:"+year +"/"+ (month)+"/"+day);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        return  view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        mBlod=(String) parent.getItemAtPosition(pos);
        PrefMang.setBlodType(mBlod);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
