package com.example.ale.keychain.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ale on 11/29/16.
 */

public class PasswordDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PasswordContract.PasswordEntry.TABLE_NAME + " (" +
                    PasswordContract.PasswordEntry._ID + " INTEGER PRIMARY KEY," +
                    PasswordContract.PasswordEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    PasswordContract.PasswordEntry.COLUMN_URL + TEXT_TYPE + COMMA_SEP +
                    PasswordContract.PasswordEntry.COLUMN_USERNAME + TEXT_TYPE + COMMA_SEP +
                    PasswordContract.PasswordEntry.COLUMN_PASSWORD + TEXT_TYPE + COMMA_SEP +
                    PasswordContract.PasswordEntry.COLUMN_NOTE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PasswordContract.PasswordEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "Password.db";

    public PasswordDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("PasswordDbHelper",SQL_CREATE_ENTRIES);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public Cursor getPasswords(SQLiteDatabase db,String name){
        String selection = name != null ? PasswordContract.PasswordEntry.COLUMN_NAME + " = ?" : name;
        String[] selectionArgs = null;
        if ( name != null ){
            selectionArgs = new String[1];
            selectionArgs[0] = name;
        }

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                PasswordContract.PasswordEntry.COLUMN_NAME + " DESC";

        Cursor c = db.query(
                PasswordContract.PasswordEntry.TABLE_NAME,                     // The table to query
                PasswordContract.PasswordEntry.projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                                // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return c;
    }

}
