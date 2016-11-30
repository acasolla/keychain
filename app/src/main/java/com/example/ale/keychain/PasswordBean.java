package com.example.ale.keychain;

import android.database.Cursor;

import com.example.ale.keychain.sql.PasswordContract;

import java.io.Serializable;

/**
 * Created by ale on 11/30/16.
 */

public class PasswordBean implements Serializable
{

    public PasswordBean(Cursor c ){
        this.name = c.getString(c.getColumnIndex(PasswordContract.PasswordEntry.COLUMN_NAME));
        this.url = c.getString(c.getColumnIndex(PasswordContract.PasswordEntry.COLUMN_URL));
        this.username = c.getString(c.getColumnIndex(PasswordContract.PasswordEntry.COLUMN_USERNAME));
        this.password = c.getString(c.getColumnIndex(PasswordContract.PasswordEntry.COLUMN_PASSWORD));
        this.note = c.getString(c.getColumnIndex(PasswordContract.PasswordEntry.COLUMN_NOTE));
    }

    private String name;
    private String url;
    private String username;
    private String password;
    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "PasswordBean{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
