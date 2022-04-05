package com.example.phonebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.phonebook.MyContentProvider";
    private static final String DATABASE_NAME = "phonebookDB.db";
    private static final String TABLE_PHONEBOOK = "phonebook";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FN = "firstName";
    public static final String COLUMN_LN = "lastName";
    public static final String COLUMN_NUM = "phoneNumber";

    private static final int DATABASE_VERSION = 1;
    public static final int CONTACTS = 1;
    public static final int CONTACT_ID = 2;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_PHONEBOOK);
    private SQLiteDatabase db;
    private UriMatcher um;
    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        boolean flag = false;
        um = new UriMatcher(UriMatcher.NO_MATCH);
        um.addURI(AUTHORITY, TABLE_PHONEBOOK, CONTACTS);
        um.addURI(AUTHORITY, TABLE_PHONEBOOK + "/#", CONTACT_ID);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        db = dbHelper.getWritableDatabase();
        Log.d("database", "Made database");
        if(db!=null) flag = true;
        return flag;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_PHONEBOOK);
        int uriType = um.match(uri);
        if(uriType != CONTACTS)
            throw new UnsupportedOperationException("Unknown URI: " + uri + " is not supported");
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = um.match(uri);
        long id = 0;
        if (uriType == CONTACTS)
            id = db.insert(TABLE_PHONEBOOK, null, values);
        else
            throw new UnsupportedOperationException("UnknownURI:" + uri + " is not supported");
        getContext().getContentResolver().notifyChange(uri, null);
        return uri.parse(TABLE_PHONEBOOK + "/" + id);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int uriType = um.match(uri);
        int rowsUpdated = 0;
        if(uriType == CONTACTS)
            rowsUpdated = db.update(TABLE_PHONEBOOK, values, selection, selectionArgs);
        else
            throw new UnsupportedOperationException("Unknown URI: " + uri + "is not supported");
        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int uriType = um.match(uri);
        int rowsDeleted = 0;
        if (uriType == CONTACTS)
            rowsDeleted = db.delete(TABLE_PHONEBOOK, selection, selectionArgs);
        else
            throw new UnsupportedOperationException("Not yet implemented");
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }



    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        //-------------------------
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String create_phonebook_table = "CREATE TABLE " +TABLE_PHONEBOOK+ "("
                    +COLUMN_ID+ " INTEGER PRIMARY KEY, "
                    +COLUMN_FN+ " TEXT, "
                    +COLUMN_LN+ " TEXT, "
                    +COLUMN_NUM+ " TEXT)";
            sqLiteDatabase.execSQL(create_phonebook_table);
        }
        //--------------------------------------------
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PHONEBOOK);
            onCreate(sqLiteDatabase);
        }
    }
}
