package com.example.contactsmanagerapp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsmanagerapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Data source
    private ContactDataBase contactDataBase;
    private ArrayList<Contacts> contactsArrayList = new ArrayList<>();
    //Adapter
    private MyAdapter myAdapter;
    //Binding
    private ActivityMainBinding mainBinding;
    private MainActivityClickHandlers handlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Data binding
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handlers = new MainActivityClickHandlers(this);
        mainBinding.setClickHandler(handlers);
        //Recycler view
        RecyclerView recyclerView = mainBinding.recView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //Database
        contactDataBase = ContactDataBase.getInstance(this);
        //View model
        MyViewModel myViewModel = new ViewModelProvider(this)
                .get(MyViewModel.class);
        //Insert a new Contact just for testing
        Contacts contact1 = new Contacts("Nick", "nickEmail.com");
        myViewModel.addNewContact(contact1);
        //Load data from database
        myViewModel.getAllContacts().observe(this, new Observer<List<Contacts>>() {
            @Override
            public void onChanged(List<Contacts> contacts) {
                contactsArrayList.clear();
                for (Contacts c : contacts) {
                    Log.v("TAGY", c.getName());
                    contactsArrayList.add(c);
                }
                myAdapter.notifyDataSetChanged();
            }
        });
        //Adapter
        myAdapter = new MyAdapter(contactsArrayList);
        //Linked the recview with the adapter
        recyclerView.setAdapter(myAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Contacts c = contactsArrayList.get(viewHolder.getAdapterPosition());
                myViewModel.deleteContact(c);
            }
        }).attachToRecyclerView(recyclerView);
    }
}