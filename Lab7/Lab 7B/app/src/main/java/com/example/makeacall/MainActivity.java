package com.example.makeacall;

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



    public void find(View view){
        EditText fin = findViewById(R.id.firstNameEdit);
        EditText lan = findViewById(R.id.lastNameEdit);
        TextView pn = findViewById(R.id.numberField);
        setContentView(R.layout.activity_main);
        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_PHONEBOOK);
        String [] projection = {COLUMN_FN, COLUMN_LN, COLUMN_NUM};
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(CONTENT_URI, projection, null, null, null);
        String num = null;
        String fn = null;
        String ln = null;
        if (cursor != null && cursor.moveToFirst()) {

            fn = String.format("%-10s\t", cursor.getString(0));
            Log.d("strings", fn);
            ln = String.format("%-10s\t", cursor.getString(1));
            Log.d("strings", ln);
            num = String.format("%-10s\t", cursor.getString(2));
            Log.d("strings", num);
        }
        while (cursor.moveToNext()){
            if(String.format("%-10s\t", fin.getText().toString().toLowerCase(Locale.ROOT)).equals(fn.toLowerCase(Locale.ROOT)) && String.format("%-10s\t", lan.getText().toString().toLowerCase(Locale.ROOT)).equals(ln.toLowerCase(Locale.ROOT))){
                pn.setText(num);
                pn.setTextColor(Color.BLACK);
                pn.setTypeface(null, Typeface.BOLD);
                pn.refreshDrawableState();
                Log.d("strings", "Phone Number: " + pn.getText().toString());
                cursor.close();
                break;
            }
        }
    }
    
    public void call(View view){
        TextView phoneNumber = findViewById(R.id.numberField);
        String current_number = "";
        if (!phoneNumber.getText().toString().isEmpty()){
            current_number = phoneNumber.getText().toString();
            try{
                Uri uri = Uri.parse("tel:" + current_number);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }catch (ActivityNotFoundException e){
                Toast.makeText(this, "Application failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}