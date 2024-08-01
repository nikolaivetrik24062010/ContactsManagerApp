package com.example.contactsmanagerapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class AddNewClickHandler {
    Contacts contact;
    Context context;
    MyViewModel viewModel;

    public AddNewClickHandler(Contacts contact, Context context, MyViewModel viewModel) {
        this.contact = contact;
        this.context = context;
        this.viewModel = viewModel;
    }

    public void onSubmitButtonClicked(View view) {
        if (contact.getName() == null || contact.getEmail() == null) {
            Toast.makeText(context, "Fields can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(context, MainActivity.class);
//            intent.putExtra("Name", contact.getName());
//            intent.putExtra("Email", contact.getEmail());
            Contacts c = new Contacts(
                    contact.getName(),
                    contact.getEmail()
            );

            viewModel.addNewContact(c);

            context.startActivity(intent);
        }
    }
}
