package com.mytechideas.appmoto.activities.formactivity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.mytechideas.appmoto.R;
import com.mytechideas.appmoto.context.AppMotoContext;
import com.mytechideas.appmoto.models.ContactsMoto;
import com.mytechideas.appmoto.preferences.PrefConsts;
import com.mytechideas.appmoto.preferences.PrefMang;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private static final String LOG_TAG=ContactsAdapter.class.getSimpleName();
    private ArrayList<ContactsMoto> mDataset= new ArrayList<ContactsMoto> ();
    private static int mSelectedContacts=0;
    private Context mContext= AppMotoContext.getAppContext();


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.contact_name)
        TextView mContactname;
        @BindView(R.id.contact_number)
        TextView mContactPhone;
        @BindView(R.id.selected_contact_box)
        CheckBox mSelectedBox;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ContactsAdapter() {

    }

    public void addNewList(ArrayList<ContactsMoto> myDataset){
        mDataset.clear();
        mDataset.addAll(myDataset);
        notifyDataSetChanged();

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.mContactname.setText(mDataset.get(position).getmName());
        holder.mContactPhone.setText(mDataset.get(position).getmPhone());

        ContactsMoto item= mDataset.get(position);
        Boolean state=item.getSelected();
        if(state){
            holder.mSelectedBox.setChecked(true);
        }
        else{
            holder.mSelectedBox.setChecked(false);
        }

        holder.mSelectedBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!state){
                    if(mSelectedContacts<=2){
                        ++mSelectedContacts;
                        item.setSelected(true);
                        holder.mSelectedBox.setChecked(true);
                    }
                    else{
                        holder.mSelectedBox.setChecked(false);
                        Toast.makeText(mContext,"Solo puedes seleccionar un máximo de 3 contactos.",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    --mSelectedContacts;
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public int getContactsSelected(){
        return mSelectedContacts;
    }
}
