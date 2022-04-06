package com.example.phonebook;
/*
    What I learned: Working with Databases in Java doesn't always make sense. I believe this is due
    to the inconsistent nature of Java's data handling, which sometimes updates itself and sometimes
    doesn't. I'm not the biggest fan.

    Zachary Evans made this app
    author: Nathan Lancaster, Zachary Evans
    version: 03/28/2022
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText firstName, lastName, phoneNumber;

    /*initializes the app and view with a few variables that get information from the user.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstName = findViewById(R.id.firstNameEdit);
        lastName = findViewById(R.id.lastNameEdit);
        phoneNumber = findViewById(R.id.numberEdit);
    }

    /*creates a new contact in the database*/
    public void newContact(View view) {
        Contact contact = new Contact(firstName.getText().toString(), lastName.getText().toString(),
                phoneNumber.getText().toString());
        ContentValues values = new ContentValues();
        values.put(MyContentProvider.COLUMN_FN, contact.getFirstName());
        values.put(MyContentProvider.COLUMN_LN, contact.getLastName());
        values.put(MyContentProvider.COLUMN_NUM, contact.getNum());
        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI,values);
        firstName.getText().clear();
        lastName.getText().clear();
        phoneNumber.getText().clear();
        Toast.makeText(this, "Succeeded in Adding!", Toast.LENGTH_SHORT).show();
    }

    /*deletes a contact from the database*/
    public void deleteContact(View view){
        String selection = "firstName = \"" + lastName.getText().toString() + "\"";
        int result = getContentResolver().delete(MyContentProvider.CONTENT_URI, selection, null);
        if(result > 0) {
            Toast.makeText(this, "Success in Deleting!", Toast.LENGTH_SHORT).show();
            firstName.getText().clear();
            lastName.getText().clear();
            phoneNumber.getText().clear();
        }else{
            Toast.makeText(this, "No Match Found", Toast.LENGTH_SHORT).show();
        }
    }

    /*clears fields in user interface*/
    public void clearFields(View view){
        firstName.getText().clear();
        lastName.getText().clear();
        phoneNumber.getText().clear();
    }

    /*shows table in database*/
    public void showAll(View view){
        firstName.getText().clear();
        lastName.getText().clear();
        phoneNumber.getText().clear();
        firstName.requestFocus();
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }

}