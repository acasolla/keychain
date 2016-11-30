package com.example.ale.keychain.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.ale.keychain.R;
import com.example.ale.keychain.sql.PasswordContract;

/**
 * Created by ale on 11/29/16.
 */

public class PasswordCursorAdapter  extends ResourceCursorAdapter {

    public PasswordCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView name = (TextView) view.findViewById(R.id.line_name);
        name.setText(cursor.getString(cursor.getColumnIndex(PasswordContract.PasswordEntry.COLUMN_NAME)));

        TextView url = (TextView) view.findViewById(R.id.line_url);
        url.setText(cursor.getString(cursor.getColumnIndex(PasswordContract.PasswordEntry.COLUMN_URL)));
    }

}
