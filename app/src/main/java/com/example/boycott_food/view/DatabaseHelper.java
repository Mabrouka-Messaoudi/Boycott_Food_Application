package com.example.boycott_food.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "boycott_food.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_BRANDS = "brands";
    private static final String COLUMN_BRAND_NAME = "brand_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE brands (id INTEGER PRIMARY KEY, brand_name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS brands");
        onCreate(db);
    }

    public void addBrand(String brandName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRAND_NAME, brandName);
        db.insert(TABLE_BRANDS, null, values);
        db.close();
    }
    public boolean isBrandBoycotted(String brandName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BRANDS, new String[]{COLUMN_BRAND_NAME}, COLUMN_BRAND_NAME + "=?",
                new String[]{brandName}, null, null, null);
        boolean boycotted = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return boycotted;
    }



}
