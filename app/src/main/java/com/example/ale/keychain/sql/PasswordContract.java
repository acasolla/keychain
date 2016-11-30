package com.example.ale.keychain.sql;

import android.provider.BaseColumns;

/**
 * Created by ale on 11/29/16.
 */

public class PasswordContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private PasswordContract() {}

    /* Inner class that defines the table contents */
    public static class PasswordEntry implements BaseColumns {
        public static final String TABLE_NAME = "password";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NOTE = "note";

        public static String[] projection = {
                PasswordEntry._ID,
                PasswordEntry.COLUMN_NAME,
                PasswordEntry.COLUMN_URL,
                PasswordEntry.COLUMN_USERNAME,
                PasswordEntry.COLUMN_PASSWORD,
                PasswordEntry.COLUMN_NOTE
        };
    }
}
