package com.example.makeacall;
/*
    The purpose of this app to retrieve the table's
    content store in MyContentProvider class of
    Phonebook app
    the table has fields: first name, last name, and phone number
    The layout has a TextView which is used to display the
    phone number of a contact whenever a first and last name were entered.
    After that, you could press the call button which would take you to the phone app with the
    number entered, ready to call

    Nathan Lancaster made this app
    authors: Nathan Lancaster, Zachary Evans
    version: 03/28/2022
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String AUTHORITY = "com.example.phonebook.MyContentProvider";
    private static final String TABLE_PHONEBOOK = "phonebook";
    public static final String COLUMN_FN = "firstName";
    public static final String COLUMN_LN = "lastName";
    public static final String COLUMN_NUM = "phoneNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


/*
find searches the database for a contact that matches the first and last name entered, it will then
replace the text in the numberfield text view with the number of the contact. If a contact does not exits
it will prompt the user that no contact with that first and last name exists.
 */
    public void find(View view){
        EditText fin = findViewById(R.id.firstNameEdit);
        EditText lan = findViewById(R.id.lastNameEdit);
        setContentView(R.layout.activity_main);
        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_PHONEBOOK);
        String [] projection = {COLUMN_FN, COLUMN_LN, COLUMN_NUM};
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(CONTENT_URI, projection, null, null, null);
        String num = null;
        String fn = null;
        String ln = null;
        /*if (cursor != null && cursor.moveToFirst()) {

            fn = String.format("%-10s\t", cursor.getString(0));
            Log.d("strings", fn);
            ln = String.format("%-10s\t", cursor.getString(1));
            Log.d("strings", ln);
            num = String.format("%-10s\t", cursor.getString(2));
            Log.d("strings", num);
        }*/
        if (!cursor.moveToNext())
            Toast.makeText(this, "Contact not found", Toast.LENGTH_SHORT);
        else
            do{
                fn = String.format("%-10s\t", cursor.getString(0));
                Log.d("strings", fn);
                ln = String.format("%-10s\t", cursor.getString(1));
                Log.d("strings", ln);
                num = String.format("%-10s\t", cursor.getString(2));
                Log.d("strings", num);
            if(String.format("%-10s\t", fin.getText().toString().toLowerCase(Locale.ROOT)).equals(fn.toLowerCase(Locale.ROOT)) && String.format("%-10s\t", lan.getText().toString().toLowerCase(Locale.ROOT)).equals(ln.toLowerCase(Locale.ROOT))){
                num = cursor.getString(2);
                TextView pn = findViewById(R.id.numberField);
                //Toast.makeText(this, "Here", Toast.LENGTH_SHORT);
                //pn.setText("Test");
                String num2 = num.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                pn.setText(num2);
                pn.setTextColor(Color.BLACK);
                pn.setTypeface(null, Typeface.BOLD);
                pn.refreshDrawableState();
                Log.d("strings", "Phone Number: " + pn.getText().toString());
                cursor.close();
                break;
            }
        }while (cursor.moveToNext());
    }
    /*
    Call takes the user to the phone app to make a call with the number retrieved from the database after using the find function
    If no number was entered, it will prompt the user that there is no number to call
     */
    public void call(View view){
        TextView phoneNumber = findViewById(R.id.numberField);
        String current_number = "";
        if (!phoneNumber.getText().toString().equals("Phone Number")){
            current_number = phoneNumber.getText().toString();
            try{
                Uri uri = Uri.parse("tel:" + current_number);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }catch (ActivityNotFoundException e){
                Toast.makeText(this, "Application failed", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "No number to call", Toast.LENGTH_SHORT).show();
        }
    }
}