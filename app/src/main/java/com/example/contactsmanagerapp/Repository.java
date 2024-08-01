package com.example.contactsmanagerapp;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final ContactDAO contactDAO;

    ExecutorService executor;
    Handler handler;

    public Repository(Application application) {
        ContactDataBase contactDataBase = ContactDataBase.getInstance(application);
        this.contactDAO = contactDataBase.getContactDAO();
        //Using for background Database operations
        executor = Executors.newSingleThreadExecutor();
        //Handler updating UI
        handler = new Handler(Looper.getMainLooper());
    }

    public void addContact(Contacts contact) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactDAO.insert(contact);
            }
        });
    }

    public void deleteContact(Contacts contact) {
        contactDAO.delete(contact);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactDAO.delete(contact);
            }
        });
    }

    public LiveData<List<Contacts>> getAllContacts() {
        return contactDAO.getAllContacts();
    }
}

