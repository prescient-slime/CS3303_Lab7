package com.example.phonebook;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstName = findViewById(R.id.firstNameEdit);
        lastName = findViewById(R.id.lastNameEdit);
        phoneNumber = findViewById(R.id.numberEdit);
    }

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

    public void clearFields(View view){
        firstName.getText().clear();
        lastName.getText().clear();
        phoneNumber.getText().clear();
    }

    public void showAll(View view){
        firstName.getText().clear();
        lastName.getText().clear();
        phoneNumber.getText().clear();
        firstName.requestFocus();
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }

}