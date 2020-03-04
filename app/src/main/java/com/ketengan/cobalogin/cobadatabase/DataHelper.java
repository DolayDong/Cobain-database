package com.ketengan.cobalogin.cobadatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {
    private static final String NAMA_DATABASE = "biodata.db";
    private static final int DATABASE_VERSION = 1;
    DataHelper(@Nullable Context context) {
        super(context, NAMA_DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE biodatamahasiswa(nim int primary key, nama text null, namakampus text null);";
        Log.d("Data", "OnCreate" + sql);
        db.execSQL(sql);

//        sql = "INSERT INTO biodatamahasiswa(nim, nama, namakampus) VALUES ('180442100019', 'Sapto Aji', 'SUDIRMAN TANGERANG');";
//        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
