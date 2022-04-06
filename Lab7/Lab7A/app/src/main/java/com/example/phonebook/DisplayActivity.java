package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @SuppressLint("Range")
    @Override
    /*Initializes and builds table*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        String [] projection = {MyContentProvider.COLUMN_ID,
                                MyContentProvider.COLUMN_FN,
                                MyContentProvider.COLUMN_LN,
                                MyContentProvider.COLUMN_NUM};
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, projection, null, null);
        int count = cursor.getCount();
        cursor.moveToFirst();
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setVerticalScrollBarEnabled(true);
        TableRow tableRow = new TableRow(this);
        TextView t0, t1, t2;
        tableRow = new TableRow(this);

        t0 = new TextView(this);
        t0.setText("Contact ID");
        t0.setTextColor(Color.RED);
        t0.setTypeface(null, Typeface.BOLD);
        t0.setPadding(20,20, 20,20 );
        tableRow.addView(t0);

        t1 = new TextView(this);
        t1.setText("First Name");
        t1.setTextColor(Color.RED);
        t1.setTypeface(null,Typeface.BOLD);
        t1.setPadding(20,20, 20,20 );
        tableRow.addView(t1);

        t2 = new TextView(this);
        t2.setText("Last Name");
        t2.setTextColor(Color.RED);
        t2.setTypeface(null,Typeface.BOLD);
        t2.setPadding(20,20, 20,20 );
        tableRow.addView(t2);

        tableLayout.addView(tableRow);
        for(int i = 0; i < count; i++){
            tableRow = new TableRow(this);

            t0 = new TextView(this);
            t0.setText(cursor.getString(cursor.getColumnIndex("firstName")));
            t0.setTextColor(Color.BLACK);
            t0.setPadding(20,20, 20,20 );
            tableRow.addView(t0);

            t1 = new TextView(this);
            t1.setText(cursor.getString(cursor.getColumnIndex("lastName")));
            t1.setTextColor(Color.BLACK);
            t1.setPadding(20,20, 20,20 );
            tableRow.addView(t1);

            t2 = new TextView(this);
            t2.setText(cursor.getString(cursor.getColumnIndex("phoneNumber")));
            t2.setTextColor(Color.BLACK);
            t2.setPadding(20,20, 20,20 );
            tableRow.addView(t2);

            tableLayout.addView(tableRow);
            if (!cursor.isLast()) cursor.moveToNext();
        }
        setContentView(tableLayout);
    }
}