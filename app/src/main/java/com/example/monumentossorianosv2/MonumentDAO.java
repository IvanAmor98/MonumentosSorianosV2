package com.example.monumentossorianosv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MonumentDAO extends SQLiteOpenHelper{

    private Context context;

    public MonumentDAO(Context context) {
        super(context, "MonumentosDB", null, 1);
        this.context = context;
    }

    private void checkTables(SQLiteDatabase db) {
        try {
            db.execSQL("DROP TABLE IF EXISTS Monumentos;");
            db.execSQL("CREATE TABLE IF NOT EXISTS Monumentos(" +
                    "idMonument INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name VARCHAR," +
                    "type INTEGER," +
                    "address VARCHAR," +
                    "phone INTEGER," +
                    "url VARCHAR," +
                    "comment VARCHAR," +
                    "image BLOB," +
                    "imageFullSize VARCHAR);");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public boolean saveMonument(MonumentDTO monument) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            checkTables(db);
            ContentValues values = new ContentValues();

            values.put("name", monument.getName());
            values.put("type", monument.getType().ordinal());
            values.put("address", monument.getAddress());
            values.put("phone", monument.getPhone());
            values.put("url", monument.getUrl());
            values.put("comment", monument.getComment());

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            monument.getImage().compress(Bitmap.CompressFormat.PNG, 100, bos);

            values.put("image", bos.toByteArray());
            values.put("imageFullSize", monument.getImageFullSize());

            db.insert("Monumentos", null, values);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public ArrayList<MonumentDTO> listMonuments() {
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();

            ArrayList<MonumentDTO> monumentList = new ArrayList<>();

            Cursor cursor = db.query("Monumentos",
                    null,    //columns to return
                    null,        //columns for the WHERE clause
                    null,        //The values for the WHERE clause
                    null,       //group the rows
                    null,       //filter by row groups
                    null);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("idMonument"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    int type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
                    String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                    int phone = cursor.getInt(cursor.getColumnIndexOrThrow("phone"));
                    String url = cursor.getString(cursor.getColumnIndexOrThrow("url"));
                    String comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
                    byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                    String imageFullSize = cursor.getString(cursor.getColumnIndexOrThrow("imageFullSize"));

                    monumentList.add(new MonumentDTO(id, name, MonumentDTO.MonumentType.values()[type], address, phone, url, comment, BitmapFactory.decodeByteArray(image, 0, image.length), imageFullSize));
                } while (cursor.moveToNext());
            }
            return monumentList;
        } catch (Exception ex) {
            Log.e("MonumentDAO", "ERROR: " + ex.getMessage());
            return null;
        } finally {
            db.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
