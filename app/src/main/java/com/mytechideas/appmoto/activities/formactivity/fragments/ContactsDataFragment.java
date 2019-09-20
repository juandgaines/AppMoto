package com.mytechideas.appmoto.activities.formactivity.fragments;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mytechideas.appmoto.R;
import com.mytechideas.appmoto.activities.formactivity.adapter.ContactsAdapter;
import com.mytechideas.appmoto.models.ContactsMoto;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsDataFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.contacts_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private ContactsAdapter contactsAdapter = new ContactsAdapter();
    private RecyclerView.LayoutManager layoutManager;
    private ContentResolver contentResolver;
    private Cursor cursor;
    private Cursor cursorPhone;




    /* */
    /*
     * Defines an array that contains column names to move from
     * the Cursor to the ListView.
     */
    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    // Define global mutable variables
    // Define a ListView object
    // Define variables for the contact the user selects
    // The contact's _ID value
    long contactId;
    // The contact's LOOKUP_KEY
    String contactKey;
    // A content URI for the selected contact
    Uri contactUri;
    private SimpleCursorAdapter cursorAdapter;

    public ContactsDataFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contacts_data, container, false);
        ButterKnife.bind(this, view);

        // Gets a CursorAdapter
        cursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        recyclerView.setAdapter(null);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mProgressBar.setVisibility(View.VISIBLE);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<ContactsMoto> contacts =getContacts();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactsAdapter.addNewList(contacts);
                        recyclerView.setAdapter(contactsAdapter);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        return view;

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public ArrayList<ContactsMoto> getContacts(){
        ArrayList<ContactsMoto> listContacts= new ArrayList<>();
        contentResolver=getContext().getContentResolver();
        cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                "upper(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");

        if (cursor.moveToFirst()) {

            do{
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Integer phoneNumber = Integer.parseInt (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                String [] ids={id};
                if (phoneNumber > 0) {

                    cursorPhone = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", ids, null);

                    if (cursorPhone.getCount() > 0) {

                        while (cursorPhone.moveToNext()) {
                            String phoneNumValue = cursorPhone.getString(
                                    cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            ContactsMoto contactSchedule = new ContactsMoto(Integer.parseInt(id), name, phoneNumValue);
                            listContacts.add(contactSchedule);

                        }

                    }
                    cursorPhone.close();
                }

            }while (cursor.moveToNext());

        } else {
            //   toast("No contacts available!")
        }
        cursor.close();

        return listContacts;
    }

    public ArrayList<ContactsMoto> getContactSelected(){
        return contactsAdapter.getSelectedContacts();
    }

}
