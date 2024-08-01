package com.example.contactsmanagerapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.contactsmanagerapp.databinding.ActivityAddNewContactBinding;
import com.example.contactsmanagerapp.databinding.ActivityMainBinding;

public class AddNewContactActivity extends AppCompatActivity {
    private ActivityAddNewContactBinding binding;
    private AddNewClickHandler addNewClickHandler;
    Contacts contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_contact);

        contacts = new Contacts();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_contact);

        MyViewModel myViewModel = new ViewModelProvider(this)
                .get(MyViewModel.class);

        binding.setContact(contacts);
        binding.setClickHandler(addNewClickHandler);

    }
}