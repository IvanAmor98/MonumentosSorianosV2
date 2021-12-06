package com.example.monumentossorianosv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;

public class MonumentDAO extends SQLiteOpenHelper{

    public MonumentDAO(Context context) {
        super(context, "MonumentosDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Monumentos(" +
                "idMonument INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR," +
                "type INTEGER," +
                "address VARCHAR," +
                "phone INTEGER," +
                "url VARCHAR," +
                "comment VARCHAR);");
    }

    public boolean saveMonument(MonumentDTO monument) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("name", monument.getName());
            values.put("type", monument.getType().ordinal());
            values.put("address", monument.getAddress());
            values.put("phone", monument.getPhone());
            values.put("url", monument.getUrl());
            values.put("comment", monument.getComment());

            db.insert("Monumentos", null, values);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
